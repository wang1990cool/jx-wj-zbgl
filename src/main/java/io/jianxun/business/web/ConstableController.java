package io.jianxun.business.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jianxun.business.domain.Constable;
import io.jianxun.business.service.DepartmentableService;

@Controller
@RequestMapping("business/constable")
public class ConstableController extends DepartmentableController<Constable, Long> {

	public ConstableController(DepartmentableService<Constable, Long> entityService) {
		super(entityService);
	}

}
