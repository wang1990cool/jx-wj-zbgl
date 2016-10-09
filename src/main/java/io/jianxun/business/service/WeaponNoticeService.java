package io.jianxun.business.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import io.jianxun.business.domain.WeaponNotice;
import io.jianxun.business.domain.stock.StockInDetail;

@Service
@Transactional(readOnly = true)
public class WeaponNoticeService extends DepartmentableService<WeaponNotice> {

	// 维护到期提前7天预警后期可改为从配置文件获取
	private long maintenance = 7;

	@Autowired
	private StockInDetailService stockInDetailService;

	@Transactional(readOnly = false)
	public void createWeaponNotice() {
		// 清空旧的提醒信息
		super.deleteInBatch(findAll());
		// 创建新的提醒信息
		LocalDate noticeDate = LocalDate.now().minusDays(maintenance);
		List<StockInDetail> details = stockInDetailService.findByMaintenanceDateLT(noticeDate);
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
		notice.setMessage(String.format("装备 %s 编号[%s]即将到达维护期限", stockInDetail.getStockIn().getWeapon(),
				stockInDetail.getStockCodePrefix() + stockInDetail.getsNo()));
		return notice;
	}

}
