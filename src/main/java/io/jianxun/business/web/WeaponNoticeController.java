package io.jianxun.business.web;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.jianxun.business.domain.WeaponNotice;
import io.jianxun.business.service.DepartmentableService;
import io.jianxun.business.service.WeaponNoticeService;
import io.jianxun.business.web.dto.ReturnDto;

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

	@RequestMapping("/maintain")
	@ResponseBody
	public ReturnDto batchMaintain(@RequestParam("ids") Long[] ids) {
		for (Long id : ids) {
			WeaponNotice notice = this.entityService.findOne(id);
			if (notice != null)
				((WeaponNoticeService) this.entityService).maintain(notice);

		}
		((WeaponNoticeService)this.entityService).createWeaponNotice();
		return ReturnDto.ok("操作成功!");
	}

}
