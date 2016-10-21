package io.jianxun.business.event;

import io.jianxun.business.domain.stock.Stock;

public class StockMinMaxEvent {

	private Stock stock;

	/**
	 * @return the stock
	 */
	public Stock getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(Stock stock) {
		this.stock = stock;
	}


	

}
