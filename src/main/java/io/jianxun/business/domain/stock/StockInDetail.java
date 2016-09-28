package io.jianxun.business.domain.stock;

import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.util.StringUtils;

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

	// 库存编码前缀
	// 格式 装备类别代码+装备名称代码+装备型号代码（单型号默认为从1开始编号）+生产日期
	private String stockCodePrefix;

	// 库存明细流水号
	// 库存编码前缀+流水号 作为装备条形码基础数据
	private String sNo;

	// 装备自带编码
	private String ownCode;

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
	public String getStockCodePrefix() {
		if (this.stockIn == null)
			return "";
		if (this.stockIn.getWeapon() == null)
			return "";
		if (this.stockIn.getWeapon().getCategory() == null)
			return "";
		if (StringUtils.isEmpty(this.stockIn.getWeapon().getCode()))
			return "";
		if (StringUtils.isEmpty(this.stockIn.getWeapon().getTypeCode()))
			return "0000";
		if (this.getStockIn().getProductionDate() == null)
			return "";
		this.stockCodePrefix = this.getStockIn().getWeapon().getCategory().getCode() + " "
				+ this.getStockIn().getWeapon().getCode() + " " + this.getStockIn().getWeapon().getTypeCode() + " "
				+ this.getStockIn().getProductionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		return stockCodePrefix;

	}

	/**
	 * @param stockCode
	 *            the stockCode to set
	 */
	public void setStockCodePrefix(String stockCodePrefix) {
		this.stockCodePrefix = stockCodePrefix;
	}

	/**
	 * @return the sNo
	 */
	public String getsNo() {
		return sNo;
	}

	/**
	 * @param sNo
	 *            the sNo to set
	 */
	public void setsNo(String sNo) {
		this.sNo = sNo;
	}
	
	@Transient
	private String getBarcode(){
		return this.stockCodePrefix + " " + this.sNo;
	}

	/**
	 * @return the ownCode
	 */
	public String getOwnCode() {
		return ownCode;
	}

	/**
	 * @param ownCode
	 *            the ownCode to set
	 */
	public void setOwnCode(String ownCode) {
		this.ownCode = ownCode;
	}

}
