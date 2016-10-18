package io.jianxun.business.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import io.jianxun.business.service.WeaponNoticeService;

@Component
public class RefreshNoticeEventListen {

	@Autowired
	private WeaponNoticeService weaponNoticeService;

	@EventListener
	void handle(RefreshNoticeEvent event) {
		weaponNoticeService.createWeaponNotice();
	}

}
