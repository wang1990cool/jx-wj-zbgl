package io.jianxun.business.web.dto;

public class StockSettingDto {

	private Long stockId = -1L;

	private Integer maxInventory = -1;
	private Integer minInventory = -1;

	
	
	
	public StockSettingDto() {
		super();
	}

	public StockSettingDto(Long stockId, Integer maxInventory, Integer minInventory) {
		super();
		this.stockId = stockId;
		this.maxInventory = maxInventory;
		this.minInventory = minInventory;
	}

	/**
	 * @return the stockId
	 */
	public Long getStockId() {
		return stockId;
	}

	/**
	 * @param stockId
	 *            the stockId to set
	 */
	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	/**
	 * @return the maxInventory
	 */
	public Integer getMaxInventory() {
		return maxInventory;
	}

	/**
	 * @param maxInventory
	 *            the maxInventory to set
	 */
	public void setMaxInventory(Integer maxInventory) {
		this.maxInventory = maxInventory;
	}

	/**
	 * @return the minInventory
	 */
	public Integer getMinInventory() {
		return minInventory;
	}

	/**
	 * @param minInventory
	 *            the minInventory to set
	 */
	public void setMinInventory(Integer minInventory) {
		this.minInventory = minInventory;
	}

}
