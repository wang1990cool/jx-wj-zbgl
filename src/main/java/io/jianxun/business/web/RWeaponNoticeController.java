package io.jianxun.business.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jianxun.business.domain.RWeaponNotice;
import io.jianxun.business.service.DepartmentableService;

@Controller
@RequestMapping("business/rweaponnotice/")
public class RWeaponNoticeController extends DepartmentableController<RWeaponNotice> {

	public RWeaponNoticeController(DepartmentableService<RWeaponNotice> entityService) {
		super(entityService);
	}

}
