package io.jianxun.business.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import io.jianxun.business.enums.Unit;
import io.jianxun.common.domain.IdEntity;

@Entity
@Table(name = "wj_zb_weapons")
public class Weapon extends IdEntity {

	private static final long serialVersionUID = -3815803388244972663L;

	@NotNull
	private String name;
	@Column(length = 500)
	private String descrip;
	// 编号
	private String code;
	// 条形码
	private String barcode;
	// 维护周期
	private int maintenanceCycle = -1;
	private String maintenanceCycleUnit = Unit.MONTH.getCode();
	// 报废周期
	private int retirementPeriod = -1;

	private String retirementPeriodUnit = Unit.YEAR.getCode();

	// 获取数据字典中装备大类
	private DataDictionary category;

	// 型号（一种装备可以有多种型号）
	private String type;
	// 型号代码
	private String typeCode;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the descrip
	 */
	public String getDescrip() {
		return descrip;
	}

	/**
	 * @param descrip
	 *            the descrip to set
	 */
	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getMaintenanceCycle() {
		return maintenanceCycle;
	}

	public void setMaintenanceCycle(int maintenanceCycle) {
		this.maintenanceCycle = maintenanceCycle;
	}

	public String getMaintenanceCycleUnit() {
		return maintenanceCycleUnit;
	}

	public void setMaintenanceCycleUnit(String maintenanceCycleUnit) {
		this.maintenanceCycleUnit = maintenanceCycleUnit;
	}

	public int getRetirementPeriod() {
		return retirementPeriod;
	}

	public void setRetirementPeriod(int retirementPeriod) {
		this.retirementPeriod = retirementPeriod;
	}

	public String getRetirementPeriodUnit() {
		return retirementPeriodUnit;
	}

	public void setRetirementPeriodUnit(String retirementPeriodUnit) {
		this.retirementPeriodUnit = retirementPeriodUnit;
	}

	/**
	 * @return the category
	 */
	@ManyToOne
	@JoinColumn(name = "category_id")
	public DataDictionary getCategory() {
		return category;
	}

	@Transient
	public String getCategoryName() {
		if (this.getCategory() != null)
			return this.getCategory().getName();
		return "";
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(DataDictionary category) {
		this.category = category;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the typeCode
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * @param typeCode
	 *            the typeCode to set
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Transient
	public String getMaintenanceCycleDisplay() {
		if (this.getMaintenanceCycle() == -1)
			return "无期限";
		else
			return this.getMaintenanceCycle() + Unit.parse(this.getMaintenanceCycleUnit());
	}

	@Transient
	public String getRetirementPeriodDisplay() {
		if (this.getRetirementPeriod() == -1)
			return "无期限";
		else
			return this.getRetirementPeriod() + Unit.parse(this.getRetirementPeriodUnit());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.jianxun.common.domain.IdEntity#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.category);
		sb.append("-");
		sb.append(this.name);
		if (this.type != null) {
			sb.append("-");
			sb.append(this.type);
		}
		return sb.toString();

	}

}
