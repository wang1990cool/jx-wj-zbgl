package io.jianxun.business.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.requisitions.RequestForm;
import io.jianxun.business.enums.RequestFormStatus;
import io.jianxun.common.service.exception.ServiceException;

@Service
@Transactional(readOnly = true)
public class RequestFormService extends DepartmentableService<RequestForm> {

	@Autowired
	private RequestFormAuditorService requestFormAuditorService;

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
		save(f);
		requestFormAuditorService.audit(f, message);

	}

}
