package io.jianxun.business.event;

import io.jianxun.business.domain.stock.StockIn;

public class RefreshStockEvent {

	//DOTO 用dto替换
	private StockIn stockin;

	/**
	 * @return the stockin
	 */
	public StockIn getStockin() {
		return stockin;
	}

	/**
	 * @param stockin
	 *            the stockin to set
	 */
	public void setStockin(StockIn stockin) {
		this.stockin = stockin;
	}

}
