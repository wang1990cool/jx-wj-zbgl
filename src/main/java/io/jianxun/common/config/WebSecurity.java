package io.jianxun.common.config;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jianxun.common.domain.user.UserDetails;

public class WebSecurity {
	/**
	 * 
	 * 通过url进行权限判断的统一入口
	 * 
	 * @param authentication
	 * @param domain
	 *            实体对象
	 * @param operate
	 *            操作
	 * @return
	 */
	public boolean check(Authentication authentication, String domain, String operate) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return false;
		}

		UserDetails user = ((UserDetails) authentication.getPrincipal());
		// 管理员拥有所有权限
		if (user.getId() != null && 1L == user.getId())
			return true;
		String perStr = getPermissionStr(domain, operate);
		Collection<? extends GrantedAuthority> as = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : as) {
			if (grantedAuthority.getAuthority().equals(perStr))
				return true;
		}
		return false;
	}

	private String getPermissionStr(String domain, String operate) {

		return domain.toUpperCase() + "_" + operate.toUpperCase();
	}

}
