package io.jianxun.business.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.jianxun.business.domain.BackNotice;
import io.jianxun.business.enums.BooleanStatus;
import io.jianxun.business.service.BackNoticeService;
import io.jianxun.business.service.DepartmentableService;
import io.jianxun.business.web.dto.ReturnDto;

@Controller
@RequestMapping("business/backnotice")
public class BackNoticeController extends DepartmentableController<BackNotice> {

	public BackNoticeController(DepartmentableService<BackNotice> entityService) {
		super(entityService);
	}

	@RequestMapping("/escheat")
	@ResponseBody
	public ReturnDto batchEscheat(@RequestParam("ids") Long[] ids) {
		for (Long id : ids) {
			BackNotice notice = this.entityService.findOne(id);

			if (notice != null && notice.getDeleted().equals(BooleanStatus.False))
				((BackNoticeService) this.entityService).escheat(notice);
		}
		((BackNoticeService) this.entityService).createNotice();
		return ReturnDto.ok("操作成功!");
	}

}
