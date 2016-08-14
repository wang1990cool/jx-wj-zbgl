package io.jianxun.common.domain.user;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import io.jianxun.common.domain.IdEntity;

@MappedSuperclass
public class User extends IdEntity {

	private static final long serialVersionUID = -7886457155724214447L;

	@NotNull
	private String username;
	@NotNull
	private String name;
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	


}
