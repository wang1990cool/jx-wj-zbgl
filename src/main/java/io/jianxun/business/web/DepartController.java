package io.jianxun.business.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jianxun.business.domain.Department;
import io.jianxun.business.service.TreeableEntityService;

@Controller
@RequestMapping(value = "/business/department")
public class DepartController extends TreeableEntityController<Department> {

	public DepartController(TreeableEntityService<Department> entityService) {
		super(entityService);
	}

}
