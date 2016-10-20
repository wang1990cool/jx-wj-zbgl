package io.jianxun.business.web;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
	
	@Override
	protected Sort createSort(String orderField, String orderDirection) {
		Direction d = Direction.valueOf(orderDirection.toUpperCase());
		if (d == null)
			d = Direction.DESC;
		Sort sort = new Sort(d, "level", orderField);
		return sort;
	}

}
