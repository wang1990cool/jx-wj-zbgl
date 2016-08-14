package io.jianxun.common.domain.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.jianxun.common.domain.IdEntity;

@Entity
@Table(name = "jx_sys_permissions")
public class Permission extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4816458616971775233L;

	// 显示名称
	//前台显示名称
	private String name;
	// 权限值
	private String value;

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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
