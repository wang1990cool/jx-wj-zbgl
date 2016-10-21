package io.jianxun.business.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jianxun.business.domain.StockNotice;
import io.jianxun.business.service.DepartmentableService;

@Controller
@RequestMapping("business/stocknotice")
public class StockMinMaxNoticeController extends DepartmentableController<StockNotice> {

	public StockMinMaxNoticeController(DepartmentableService<StockNotice> entityService) {
		super(entityService);
	}
}
