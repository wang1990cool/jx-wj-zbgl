package io.jianxun.business.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import io.jianxun.business.domain.stock.StockIn;
import io.jianxun.business.service.StockInDetailService;
import io.jianxun.business.service.StockService;

@Component
public class RefreshStockEventListen {

	@Autowired
	private StockInDetailService stockInDetailService;
	@Autowired
	private StockService stockService;

	@EventListener
	void handle(RefreshStockEvent event) {
		StockIn stockIn = event.getStockin();
		stockInDetailService.save(stockIn);
		stockService.refrashStock(stockIn);
	}

}
