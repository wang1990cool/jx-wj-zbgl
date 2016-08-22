package io.jianxun.business.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jianxun.business.domain.Department;
import io.jianxun.common.service.TreeableEntityService;
import io.jianxun.common.web.TreeableEntityController;

@Controller
@RequestMapping(value = "/business/department")
public class DepartController extends TreeableEntityController<Department, Long> {

	public DepartController(TreeableEntityService<Department, Long> entityService) {
		super(entityService);
	}

}
