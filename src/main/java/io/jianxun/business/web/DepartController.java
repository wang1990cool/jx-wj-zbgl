package io.jianxun.business.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jianxun.business.domain.Department;
import io.jianxun.common.repository.EntityRepository;
import io.jianxun.common.web.EntityController;

@Controller
@RequestMapping(value = "depart")
public class DepartController extends EntityController<Department, Long> {

	public DepartController(EntityRepository<Department, Long> entityRepository) {
		super(entityRepository);
	}

}
