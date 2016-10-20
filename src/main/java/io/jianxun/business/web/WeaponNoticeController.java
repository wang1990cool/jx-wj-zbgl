package io.jianxun.business.web;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.jianxun.common.web.EntityController#createSort(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	protected Sort createSort(String orderField, String orderDirection) {
		Direction d = Direction.valueOf(orderDirection.toUpperCase());
		if (d == null)
			d = Direction.DESC;
		Sort sort = new Sort(d, "level", orderField);
		return sort;
	}

}
