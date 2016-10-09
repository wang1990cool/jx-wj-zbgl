package io.jianxun.business.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.jianxun.business.service.WeaponNoticeService;

@Component
public class ScheduledTasks {

	@Autowired
	private WeaponNoticeService weaponNoticeService;

	@Scheduled(fixedRate = 5000)
	public void te() {
		weaponNoticeService.createWeaponNotice();
	}

}
