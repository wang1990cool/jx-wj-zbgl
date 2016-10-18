package io.jianxun.business.service;

import java.time.LocalDate;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.Department;
import io.jianxun.business.domain.Weapon;
import io.jianxun.business.domain.requisitions.RequestForm;
import io.jianxun.business.domain.stock.StockInDetail;
import io.jianxun.business.enums.RequestFormStatus;
import io.jianxun.business.event.AdjustStockEvent;
import io.jianxun.business.event.RefreshNoticeEvent;
import io.jianxun.common.service.exception.ServiceException;

@Service
@Transactional(readOnly = true)
public class RequestFormService extends DepartmentableService<RequestForm> {

	@Autowired
	private RequestFormAuditorService requestFormAuditorService;
	@Autowired
	private StockInDetailService stockInDetailService;

	private final ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	public RequestFormService(ApplicationEventPublisher applicationEventPublisher) {
		super();
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Transactional(readOnly = false)
	public void up(Long id) {
		RequestForm r = findOne(id);
		if (r != null) {
			r.setStatus(RequestFormStatus.UP);
			requestFormAuditorService.audit(r, "用户提交");
			save(r);

		}
	}

	@Transactional(readOnly = false)
	public void commit(Long id) {
		RequestForm r = findOne(id);
		if (r != null) {
			r.setStatus(RequestFormStatus.COMMIT);
			save(r);
		}
	}

	@Transactional(readOnly = false)
	public void back(Long id) {
		RequestForm r = findOne(id);
		if (r != null) {
			r.setStatus(RequestFormStatus.BACK);
			save(r);
		}
	}

	@Transactional(readOnly = false)
	public void audit(RequestForm f, String message) {
		if (f.getStatus().equals(RequestFormStatus.CREATE) || f.getStatus().equals(RequestFormStatus.BACK))
			throw new ServiceException("申请未提交不能审核");
		if (f.getStatus().equals(RequestFormStatus.COMMIT))
			throw new ServiceException("申请已审核，不能重复操作");
		if (f.getStatus().equals(RequestFormStatus.UP))
			f.setStatus(RequestFormStatus.COMMIT);
		if (StringUtils.isEmpty(message))
			message = "审核通过";
		save(f);
		requestFormAuditorService.audit(f, message);

	}

	@Transactional(readOnly = false)
	public void back(RequestForm f, String message) {
		if (f.getStatus().equals(RequestFormStatus.CREATE) || f.getStatus().equals(RequestFormStatus.BACK))
			throw new ServiceException("申请未提交不能退回");
		if (f.getStatus().equals(RequestFormStatus.COMMIT))
			throw new ServiceException("申请已审核，不能退回");
		if (f.getStatus().equals(RequestFormStatus.UP))
			f.setStatus(RequestFormStatus.BACK);
		if (StringUtils.isEmpty(message))
			message = "审核未通过";
		requestFormAuditorService.audit(f, message);

	}

	@Transactional(readOnly = false)
	public void up(RequestForm f, String message) {
		if (f.getStatus().equals(RequestFormStatus.CREATE) || f.getStatus().equals(RequestFormStatus.BACK)) {
			f.setStatus(RequestFormStatus.UP);
			if (StringUtils.isEmpty(message))
				message = "提交成功";
			requestFormAuditorService.audit(f, message);
		} else
			throw new ServiceException("状态错误不能提交");

	}

	@Transactional(readOnly = false)
	public void sysout(RequestForm f) {
		Page<StockInDetail> p = stockInDetailService.findAll(new PageRequest(0, f.getCapacity(), Direction.DESC, "id"));
		if (p.getContent() != null && !p.getContent().isEmpty()) {
			f.getDetails().addAll(p.getContent());
			f.setStatus(RequestFormStatus.ENROLLMENT);
			save(f);
			requestFormAuditorService.audit(f, "登记成功");
		} else
			throw new ServiceException("未选择任何装备");

	}

	@Transactional(readOnly = false)
	public void finish(RequestForm f, String message) {
		if (!f.getStatus().equals(RequestFormStatus.ENROLLMENT))
			throw new ServiceException("申请信息未登记不能确认");
		if (f.getStatus().equals(RequestFormStatus.FINISH))
			throw new ServiceException("申请已确认，不能重复操作");
		f.setStatus(RequestFormStatus.FINISH);
		if (StringUtils.isEmpty(message))
			message = "确认发放";
		f.setRequiredDate(LocalDate.now());
		save(f);
		requestFormAuditorService.audit(f, message);
		// 调整库存
		adjustStock(f);
		//刷新提醒
		applicationEventPublisher.publishEvent(new RefreshNoticeEvent());
	}

	private void adjustStock(RequestForm f) {
		Department source = getSourceDepart(f);
		Department destination = f.getDepart();
		Weapon weapon = f.getWeapon();
		Set<StockInDetail> details = f.getDetails();
		adjustStockEvent(source, destination, weapon, details);

	}

	private Department getSourceDepart(RequestForm f) {
		Long parentId = f.getDepart().getpId();
		Department source = departmentService.findOne(parentId);
		if (source == null)
			throw new ServiceException("获取机构信息出错");
		return source;
	}

	private void adjustStockEvent(Department source, Department destination, Weapon weapon,
			Set<StockInDetail> details) {
		AdjustStockEvent event = new AdjustStockEvent();
		event.setSource(source);
		event.setDestination(destination);
		event.setWeapon(weapon);
		event.setDetails(details);
		applicationEventPublisher.publishEvent(event);
	}

}
