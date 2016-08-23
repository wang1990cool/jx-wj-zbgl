package io.jianxun.business.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jianxun.business.domain.requisitions.RequestForm;
import io.jianxun.business.service.DepartmentableService;

@Controller
@RequestMapping("business/requestform")
public class RequestFormController extends DepartmentableController<RequestForm, Long> {

	public RequestFormController(DepartmentableService<RequestForm, Long> entityService) {
		super(entityService);
	}

}
