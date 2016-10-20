package io.jianxun.business.web;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.jianxun.business.domain.RWeaponNotice;
import io.jianxun.business.service.DepartmentableService;
import io.jianxun.business.service.RWeaponNoticeService;
import io.jianxun.business.web.dto.ReturnDto;

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
	
	@RequestMapping("/scrap")
	@ResponseBody
	public ReturnDto batchScrap(@RequestParam("ids") Long[] ids) {
		for (Long id : ids) {
			RWeaponNotice notice = this.entityService.findOne(id);
			if (notice != null)
				((RWeaponNoticeService) this.entityService).scrap(notice);

		}
		((RWeaponNoticeService)this.entityService).createNotice();
		return ReturnDto.ok("操作成功!");
	}

}
