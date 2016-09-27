package io.jianxun.common.domain.user;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.OrderBy;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.google.common.collect.Sets;

import io.jianxun.business.domain.Role;
import io.jianxun.business.enums.BooleanStatus;
import io.jianxun.common.domain.IdEntity;

@MappedSuperclass
public class UserDetails extends IdEntity implements org.springframework.security.core.userdetails.UserDetails {

	private static final long serialVersionUID = -6278197000645900257L;

	private String username;

	private String name;

	private String password;

	// 用户失效
	private boolean accountNonExpired = true;
	// 用户锁定
	private boolean accountNonLocked = true;
	// 密码失效
	private boolean credentialsNonExpired = true;
	// 用户可用
	private boolean enabled = true;

	// 角色信息
	private Set<Role> roles = Sets.newHashSet();

	@Override
	@Transient
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (this.roles.size() == 0)
			return AuthorityUtils.commaSeparatedStringToAuthorityList("");
		StringBuilder commaBuilder = new StringBuilder();
		for (Role role : roles) {
			if (BooleanStatus.TRUE.equals(role.getDeleted()))
				continue;
			List<String> permissions = role.getPermissions();
			if (permissions == null || permissions.size() < 1) {
				continue;
			}
			for (String permission : permissions) {
				commaBuilder.append(permission.toUpperCase()).append(",");
			}
		}
		String authorities = commaBuilder.substring(0, commaBuilder.length() - 1);
		return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
	}

	@NotNull
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@NotNull
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the accountNonExpired
	 */
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	/**
	 * @param accountNonExpired
	 *            the accountNonExpired to set
	 */
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	/**
	 * @return the accountNonLocked
	 */
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	/**
	 * @param accountNonLocked
	 *            the accountNonLocked to set
	 */
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	/**
	 * @return the credentialsNonExpired
	 */
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	/**
	 * @param credentialsNonExpired
	 *            the credentialsNonExpired to set
	 */
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return this.accountNonExpired && this.accountNonLocked && this.credentialsNonExpired;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "jx_sys_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	// Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	// 集合按id排序.
	@OrderBy("id")
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
