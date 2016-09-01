package io.jianxun.business.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
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

	@Transient
	public String getMaintenanceCycleDisplay() {
		if (this.getMaintenanceCycle() == -1)
			return "无期限";
		else
			return this.getMaintenanceCycle() + Unit.parse(this.getMaintenanceCycleUnit());
	}
	
	@Transient
	public String getRetirementPeriodDisplay(){
		if (this.getRetirementPeriod() == -1)
			return "无期限";
		else
			return this.getRetirementPeriod() + Unit.parse(this.getRetirementPeriodUnit());
	}

}
