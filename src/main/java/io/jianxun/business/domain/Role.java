package io.jianxun.business.domain;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;

@Entity
@Table(name = "jx_sys_roles")
public class Role extends BusinessBaseEntity {

	private static final long serialVersionUID = -351239566771402447L;
	/**
	 * 角色显示名称
	 */
	@NotNull
	private String name;
	/**
	 * 角色值
	 */
	@NotNull
	private String code;

	// 权限列表
	private List<String> permissions = Lists.newArrayList();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "jx_sys_role_pers")
	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

}
