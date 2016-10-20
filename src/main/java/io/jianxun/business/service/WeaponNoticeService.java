package io.jianxun.business.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import io.jianxun.business.domain.NoticeEntity;
import io.jianxun.business.domain.WeaponNotice;
import io.jianxun.business.domain.stock.StockInDetail;

@Service
@Transactional(readOnly = true)
public class WeaponNoticeService extends DepartmentableService<WeaponNotice> {

	// 维护到期提前7天预警后期可改为从配置文件获取
	private long maintenance = 7;

	@Autowired
	private StockInDetailService stockInDetailService;

	// 维护到期
	@Transactional(readOnly = false)
	public void createWeaponNotice() {
		// 清空旧的提醒信息
		super.deleteInBatch(findAll());
		// 创建新的提醒信息
		LocalDate noticeDate = LocalDate.now().plusDays(maintenance);
		List<StockInDetail> details = stockInDetailService.findByMaintenanceNoticeDateLT(noticeDate);
		if (details != null && details.size() > 0) {
			List<WeaponNotice> notices = Lists.newArrayList();
			for (StockInDetail stockInDetail : details) {
				notices.add(createNoiceMessage(stockInDetail));
			}
			save(notices);
		}

	}

	private WeaponNotice createNoiceMessage(StockInDetail stockInDetail) {
		WeaponNotice notice = new WeaponNotice();
		notice.setDepart(stockInDetail.getDepart());
		long d = ChronoUnit.DAYS.between(LocalDate.now(), stockInDetail.getMaintenanceNoticeDate());
		if (d > 0)
			notice.setMessage(String.format("装备 %s 编号[%s] 还有%d天即将到达维护期限", stockInDetail.getStockIn().getWeapon(),
					stockInDetail.getStockCodePrefix() + stockInDetail.getsNo(), d));
		else {
			notice.setLevel(NoticeEntity.OUT_TIME_LEVEL);
			notice.setMessage(String.format("装备 %s 编号[%s] 超过维护期限 %d 天", stockInDetail.getStockIn().getWeapon(),
					stockInDetail.getStockCodePrefix() + stockInDetail.getsNo(), 0-d));
		}
		return notice;
	}

}
