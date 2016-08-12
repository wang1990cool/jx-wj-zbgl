package io.jianxun.common.domain.user;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.jianxun.common.domain.IdEntity;

@Entity
@Table(name = "jx_sys_roles")
public class Role extends IdEntity {

	private static final long serialVersionUID = -351239566771402447L;
	/**
	 * 角色显示名称
	 */
	@NotNull
	private String name;
	/**
	 * 角色值 eg:ADMIN,USER
	 */
	@NotNull
	private String roleCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

}
