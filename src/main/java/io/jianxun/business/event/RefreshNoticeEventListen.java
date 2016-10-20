package io.jianxun.business.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import io.jianxun.business.service.BackNoticeService;
import io.jianxun.business.service.RWeaponNoticeService;
import io.jianxun.business.service.WeaponNoticeService;

@Component
public class RefreshNoticeEventListen {

	@Autowired
	private WeaponNoticeService weaponNoticeService;
	@Autowired
	private RWeaponNoticeService rWeaponNoticeService;
	@Autowired
	private BackNoticeService backNoticeService; 

	@EventListener
	void handle(RefreshNoticeEvent event) {
		weaponNoticeService.createWeaponNotice();
		rWeaponNoticeService.createNotice();
		backNoticeService.createNotice();
	}

}
