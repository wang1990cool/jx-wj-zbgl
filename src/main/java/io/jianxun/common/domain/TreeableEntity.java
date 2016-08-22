package io.jianxun.common.domain;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class TreeableEntity extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -525047097965064302L;

	// 上级代码
	private Long pId;

	// 层级代码
	private String levelCode;

	// 显示顺序
	private int xxsx = 9999;

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public int getXxsx() {
		return xxsx;
	}

	public void setXxsx(int xxsx) {
		this.xxsx = xxsx;
	}

}
