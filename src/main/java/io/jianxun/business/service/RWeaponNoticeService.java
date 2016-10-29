package io.jianxun.business.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import io.jianxun.business.domain.NoticeEntity;
import io.jianxun.business.domain.RWeaponNotice;
import io.jianxun.business.domain.stock.StockInDetail;

@Service
@Transactional(readOnly = true)
public class RWeaponNoticeService extends DepartmentableService<RWeaponNotice> {

	// 维护到期提前7天预警后期可改为从配置文件获取
	private long maintenance = 7;

	@Autowired
	private StockInDetailService stockInDetailService;

	// 维护到期
	@Transactional(readOnly = false)
	public void createNotice() {
		// 清空旧的提醒信息
		super.deleteInBatch(findAll());
		// 创建新的提醒信息
		LocalDate noticeDate = LocalDate.now().plusDays(maintenance);
		List<StockInDetail> details1 = stockInDetailService.findByRetirementPeriodNoticeDateLT(noticeDate);
		if (details1 != null && details1.size() > 0) {
			List<RWeaponNotice> notices = Lists.newArrayList();
			for (StockInDetail stockInDetail : details1) {
				notices.add(createNoiceMessage(stockInDetail));
			}
			save(notices);
		}

	}

	private RWeaponNotice createNoiceMessage(StockInDetail stockInDetail) {
		RWeaponNotice notice = new RWeaponNotice();
		notice.setDepart(stockInDetail.getDepart());
		notice.setDetail(stockInDetail);
		long d = ChronoUnit.DAYS.between(LocalDate.now(), stockInDetail.getRetirementPeriodNoticeDate());
		if (d > 0)
			notice.setMessage(String.format("装备 %s 编号[%s] 还有%d天即将到达维护期限", stockInDetail.getStockIn().getWeapon(),
					stockInDetail.getStockCodePrefix() + stockInDetail.getsNo(), d));
		else {
			notice.setLevel(NoticeEntity.OUT_TIME_LEVEL);
			notice.setMessage(String.format("装备 %s 编号[%s] 超过维护报废期限 %d 天", stockInDetail.getStockIn().getWeapon(),
					stockInDetail.getStockCodePrefix() + stockInDetail.getsNo(), 0 - d));
		}
		return notice;
	}

	@Transactional(readOnly = false)
	public void scrap(RWeaponNotice notice) {
		StockInDetail detail = notice.getDetail();
		if (detail != null)
			stockInDetailService.scrap(detail);
	}

}
