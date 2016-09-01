package io.jianxun.business.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@Override
	protected void otherPageDate(Model model) {
		super.otherPageDate(model);
		// 测试数据
		if (getEntityService().findAll().isEmpty()) {
			for (int i = 0; i < 40; i++) {
				Weapon entity = new Weapon();
				entity.setBarcode("1111" + "-" + i);
				entity.setCode("999" + "-" + i);
				entity.setName("测试装备" + "-" + i);
				getEntityService().save(entity);
			}
		}
	}

}
