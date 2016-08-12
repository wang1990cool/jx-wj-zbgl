package io.jianxun.common.domain.user;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

	private static final long serialVersionUID = -6278197000645900257L;

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (this.user == null)
			return AuthorityUtils.commaSeparatedStringToAuthorityList("");
		List<Role> roles = user.getRoles();
		if (roles == null || roles.size() < 1) {
			return AuthorityUtils.commaSeparatedStringToAuthorityList("");
		}
		StringBuilder commaBuilder = new StringBuilder();
		for (Role role : roles) {
			commaBuilder.append(role.getRoleCode()).append(",");
		}
		String authorities = commaBuilder.substring(0, commaBuilder.length() - 1);
		return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
	}

	@Override
	public String getPassword() {
		return user == null ? "" : user.getPassword();
	}

	@Override
	public String getUsername() {
		return user == null ? "" : user.getLoginName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
