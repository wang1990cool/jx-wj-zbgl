package io.jianxun.business.domain.stock;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.jianxun.common.domain.IdEntity;

/**
 * 库存明细
 * 
 * @author tt
 *
 */
@Entity
@Table(name = "jx_wj_stock_details")
public class StockInDetail extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5850340513999006022L;

	// 入库单据
	private StockIn stockIn;

	// 库存编码
	// 格式 装备类别代码+装备名称代码+装备型号代码（单型号默认为从1开始编号）+生产日期+流水号
	// 作为装备条形码
	private String stockCode;

	/**
	 * @return the stockIn
	 */
	@ManyToOne
	@JoinColumn(name = "stockin_id")
	public StockIn getStockIn() {
		return stockIn;
	}

	/**
	 * @param stockIn
	 *            the stockIn to set
	 */
	public void setStockIn(StockIn stockIn) {
		this.stockIn = stockIn;
	}

	/**
	 * @return the stockCode
	 */
	public String getStockCode() {
		return stockCode;
	}

	/**
	 * @param stockCode
	 *            the stockCode to set
	 */
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

}
