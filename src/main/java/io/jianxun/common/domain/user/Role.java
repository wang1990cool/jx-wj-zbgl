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
	 * 角色值
	 */
	@NotNull
	private String code;

	// 所属用户id
	private Long owner = -1L;

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

	/**
	 * @return the owner
	 */
	public Long getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(Long owner) {
		this.owner = owner;
	}

}
