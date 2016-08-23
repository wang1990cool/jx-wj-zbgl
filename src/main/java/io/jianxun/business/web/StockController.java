package io.jianxun.business.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jianxun.business.domain.stock.Stock;
import io.jianxun.business.service.DepartmentableService;

@Controller
@RequestMapping("business/stock")
public class StockController extends DepartmentableController<Stock, Long> {

	public StockController(DepartmentableService<Stock, Long> entityService) {
		super(entityService);
	}

}
