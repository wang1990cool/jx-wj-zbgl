package io.jianxun.business.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jianxun.business.domain.stock.Stock;
import io.jianxun.business.service.DepartmentableService;

@Controller
@RequestMapping("business/stock")
public class StockController extends DepartmentableController<Stock> {

	public StockController(DepartmentableService<Stock> entityService) {
		super(entityService);
	}

}
