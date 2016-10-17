package io.jianxun.business.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "wj_zb_departs")
public class Department extends TreeableEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6530993116143960507L;

	private String code;

	@NotNull
	private String name;
	// 简称
	private String simpleName;

	private String descrip;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	@Transient
	public String getDisplayName() {
		return simpleName == null ? name : simpleName;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

}
