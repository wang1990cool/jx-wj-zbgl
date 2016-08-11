package io.jianxun.business.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jianxun.business.domain.Department;
import io.jianxun.common.service.EntityService;
import io.jianxun.common.web.EntityController;

@Controller
@RequestMapping(value = "depart")
public class DepartController extends EntityController<Department, Long> {

	public DepartController(EntityService<Department, Long> entityService) {
		super(entityService);
	}

}
