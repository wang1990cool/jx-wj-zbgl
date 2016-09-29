package io.jianxun.business.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jianxun.business.domain.Constable;
import io.jianxun.business.domain.validator.ConstableValidator;
import io.jianxun.business.service.DepartmentableService;

@Controller
@RequestMapping("business/constable")
public class ConstableController extends DepartmentableController<Constable> {

	public ConstableController(DepartmentableService<Constable> entityService) {
		super(entityService);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder b) {
		super.initBinder(b);
		b.addValidators(constableValidator);
	}

	@Autowired
	private ConstableValidator constableValidator;

}
