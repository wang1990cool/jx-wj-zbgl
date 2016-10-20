package io.jianxun.business.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.jianxun.business.service.BackNoticeService;
import io.jianxun.business.service.RWeaponNoticeService;
import io.jianxun.business.service.WeaponNoticeService;

@Component
public class ScheduledTasks {

	@Autowired
	private WeaponNoticeService weaponNoticeService;
	@Autowired
	private RWeaponNoticeService rWeaponNoticeService;
	@Autowired
	private BackNoticeService backNoticService;

	// 维护提醒
	@Scheduled(cron = "0 0 0 * * ?")
	public void wh() {
		weaponNoticeService.createWeaponNotice();
	}

	// 报废提醒
	@Scheduled(cron = "0 0 0 * * ?")
	public void bf() {
		rWeaponNoticeService.createNotice();
	}

	// 归还提醒
	@Scheduled(cron = "0 0 0 * * ?")
	public void bc() {
		backNoticService.createNotice();
	}

}
