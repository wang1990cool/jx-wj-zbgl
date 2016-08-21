package io.jianxun.business.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.jianxun.common.domain.IdEntity;

@Entity
@Table(name = "wj_zb_departs")
public class Department extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6530993116143960507L;

	@NotNull
	private String name;

	// 上级代码
	private Long pId;

	// 机构层级代码
	private String levelCode;

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
	 * @return the pId
	 */
	public Long getpId() {
		return pId;
	}

	/**
	 * @param pId
	 *            the pId to set
	 */
	public void setpId(Long pId) {
		this.pId = pId;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

}
