package io.jianxun.business.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "wj_zb_constables")
public class Constable extends DepartmentEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7630696083585158752L;

	// 姓名
	private String name;
	// 身份证号
	private String card;
	// 性别
	private String grender;
	// 警员编号
	private String code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getGrender() {
		return grender;
	}

	public void setGrender(String grender) {
		this.grender = grender;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
