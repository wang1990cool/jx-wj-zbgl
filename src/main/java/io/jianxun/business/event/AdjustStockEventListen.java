package io.jianxun.business.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import io.jianxun.business.service.StockService;

@Component
public class AdjustStockEventListen {

	@Autowired
	private StockService stockService;

	@EventListener
	void handle(AdjustStockEvent event) {
		stockService.refrashStock(event.getSource(), event.getDestination(), event.getWeapon(), event.getDetails());
	}

}
