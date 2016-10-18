package io.jianxun.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.stock.StockIn;
import io.jianxun.business.event.RefreshNoticeEvent;
import io.jianxun.business.event.RefreshStockEvent;

@Service
@Transactional(readOnly = true)
public class StockInService extends DepartmentableService<StockIn> {

	private final ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	public StockInService(ApplicationEventPublisher applicationEventPublisher) {
		super();
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	@Transactional(readOnly = false)
	public StockIn save(StockIn stock) {
		super.save(stock);
		this.entityRepository.flush();
		// 刷新库存
		refreshStock(stock);
		//刷新提醒
		applicationEventPublisher.publishEvent(new RefreshNoticeEvent());
		return stock;

	}

	private void refreshStock(StockIn si) {
		RefreshStockEvent event = new RefreshStockEvent();
		event.setStockin(si);
		applicationEventPublisher.publishEvent(event);
	}

}
