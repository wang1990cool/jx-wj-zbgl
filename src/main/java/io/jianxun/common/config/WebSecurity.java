package io.jianxun.common.config;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jianxun.business.domain.User;

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
		if(StringUtils.isEmpty(domain))
			return false;
		if(StringUtils.isEmpty(operate))
			return false;
		// 路径中business/ajax统一为只要登录就能使用
		if ("ajax".equals(operate))
			return true;
		User user = ((User) authentication.getPrincipal());
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
