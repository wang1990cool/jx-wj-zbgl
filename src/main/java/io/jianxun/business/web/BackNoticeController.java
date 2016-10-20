package io.jianxun.business.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jianxun.business.domain.BackNotice;
import io.jianxun.business.service.DepartmentableService;

@Controller
@RequestMapping("business/backnotice")
public class BackNoticeController extends DepartmentableController<BackNotice> {

	public BackNoticeController(DepartmentableService<BackNotice> entityService) {
		super(entityService);
	}

}
