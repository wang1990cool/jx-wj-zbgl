package io.jianxun.business.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.jianxun.common.domain.IdEntity;

/**
 * 器械信息
 * 
 * @author tt
 *
 */
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

}
