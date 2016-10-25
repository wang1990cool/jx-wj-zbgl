package io.jianxun.business.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import io.jianxun.business.service.BackNoticeService;
import io.jianxun.business.service.RWeaponNoticeService;
import io.jianxun.business.service.StockInDetailService;
import io.jianxun.business.service.WeaponNoticeService;

@Component
public class RefreshStockDetailEventListen {

	@Autowired
	private WeaponNoticeService weaponNoticeService;
	@Autowired
	private RWeaponNoticeService rWeaponNoticeService;
	@Autowired
	private BackNoticeService backNoticeService;
	@Autowired
	private StockInDetailService stockInDetailService;

	@EventListener
	void handle(RefreshStockDetailEvent event) {
		stockInDetailService.refreshWeaponInfo(event.getWeapon());
		weaponNoticeService.createWeaponNotice();
		rWeaponNoticeService.createNotice();
		backNoticeService.createNotice();
	}

}
