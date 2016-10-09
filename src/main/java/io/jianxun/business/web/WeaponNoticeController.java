package io.jianxun.business.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jianxun.business.domain.WeaponNotice;
import io.jianxun.business.service.DepartmentableService;

@Controller
@RequestMapping("business/weaponnotice")
public class WeaponNoticeController extends DepartmentableController<WeaponNotice> {

	public WeaponNoticeController(DepartmentableService<WeaponNotice> entityService) {
		super(entityService);
	}

}
