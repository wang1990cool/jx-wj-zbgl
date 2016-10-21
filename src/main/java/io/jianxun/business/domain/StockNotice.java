package io.jianxun.business.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.jianxun.business.domain.stock.Stock;

@Entity
@Table(name = "jx_zb_stocknotices")
public class StockNotice extends NoticeEntity {

	private static final long serialVersionUID = 178168558377533590L;

	private Stock stock;

	/**
	 * @return the stock
	 */
	@ManyToOne
	@JoinColumn(name = "stock_id")
	public Stock getStock() {
		return stock;
	}

	/**
	 * @param stock
	 *            the stock to set
	 */
	public void setStock(Stock stock) {
		this.stock = stock;
	}

}
