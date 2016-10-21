package io.jianxun.business.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import io.jianxun.business.service.StockNoticeService;

@Component
public class StockMinMaxEventListen {
	
	@Autowired
	private  StockNoticeService stockNoticeService;

	@EventListener
	void handle(StockMinMaxEvent event) {
		stockNoticeService.createNotice(event.getStock());
	}

}
