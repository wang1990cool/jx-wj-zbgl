package io.jianxun.business.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.jianxun.common.domain.user.UserDetails;

@Entity
@Table(name = "jx_sys_users")
public class User extends UserDetails {

	private static final long serialVersionUID = -6694320630153506903L;

	// 对应警员
	private Constable constable;

	@ManyToOne
	@JoinColumn(name = "cons_id")
	public Constable getConstable() {
		return constable;
	}

	public void setConstable(Constable constable) {
		this.constable = constable;
	}

}
