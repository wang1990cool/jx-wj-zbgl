package io.jianxun.business.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.jianxun.business.service.WeaponNoticeService;

@Component
public class ScheduledTasks {

	@Autowired
	private WeaponNoticeService weaponNoticeService;

	// 维护提醒
	@Scheduled(cron="0 0 0 * * ?")
	public void wh() {
		weaponNoticeService.createWeaponNotice();
	}

	// 归还提醒
	@Scheduled(cron="0 0 0 * * ?")
	public void gh() {
		weaponNoticeService.createWeaponNotice();
	}

}
