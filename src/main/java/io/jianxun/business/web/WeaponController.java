package io.jianxun.business.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jianxun.business.domain.Weapon;
import io.jianxun.common.service.EntityService;
import io.jianxun.common.web.EntityController;

@Controller
@RequestMapping("/business/weapon")
public class WeaponController extends EntityController<Weapon, Long> {

	public WeaponController(EntityService<Weapon, Long> entityService) {
		super(entityService);
	}

}
