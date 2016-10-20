package io.jianxun.business.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import io.jianxun.business.domain.BackNotice;
import io.jianxun.business.domain.NoticeEntity;
import io.jianxun.business.domain.requisitions.RequestForm;
import io.jianxun.business.domain.stock.StockInDetail;

@Service
@Transactional(readOnly = true)
public class BackNoticeService extends DepartmentableService<BackNotice> {

	// 维护到期提前7天预警后期可改为从配置文件获取
	private long maintenance = 7;

	@Autowired
	private RequestFormService requestFormService;

	// 归还到期
	@Transactional(readOnly = false)
	public void createNotice() {
		// 清空旧的提醒信息
		super.deleteInBatch(findAll());
		// 创建新的提醒信息
		LocalDate noticeDate = LocalDate.now().plusDays(maintenance);
		List<RequestForm> details = requestFormService.findByReturnDateLT(noticeDate);
		if (details != null && details.size() > 0) {
			List<BackNotice> notices = Lists.newArrayList();
			for (RequestForm requestForm : details) {
				notices.addAll(createNoiceMessage(requestForm));
			}
			save(notices);
		}

	}

	private List<BackNotice> createNoiceMessage(RequestForm requestForm) {
		Set<StockInDetail> details = requestForm.getDetails();
		List<BackNotice> notices = Lists.newArrayList();
		long d = ChronoUnit.DAYS.between(LocalDate.now(), requestForm.getReturnDate());
		for (StockInDetail stockInDetail : details) {
			BackNotice notice = new BackNotice();
			notice.setDepart(requestForm.getDepart());
			if (d > 0)
				notice.setMessage(String.format("装备 %s 编号[%s] 还有%d天即将到达归还期限", stockInDetail.getStockIn().getWeapon(),
						stockInDetail.getStockCodePrefix() + stockInDetail.getsNo(), d));
			else {
				notice.setLevel(NoticeEntity.OUT_TIME_LEVEL);
				notice.setMessage(String.format("装备 %s 编号[%s] 超过维护归还期限 %d 天", stockInDetail.getStockIn().getWeapon(),
						stockInDetail.getStockCodePrefix() + stockInDetail.getsNo(), 0 - d));
			}
			notices.add(notice);
		}

		return notices;
	}

}
