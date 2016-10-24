package io.jianxun.common.config;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jianxun.business.domain.User;

public class WebSecurity {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

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
			logger.error("登录验证信息为空");
			return false;
		}
		if (StringUtils.isEmpty(domain))
			return false;
		if (StringUtils.isEmpty(operate))
			return false;
		// 路径中business/ajax统一为只要登录就能使用
		if ("ajax".equals(operate)) {
			logger.debug("执行ajax操作");
			return true;
		}
		String perStr = getPermissionStr(domain, operate);
		
		try {
			// 管理员拥有所有权限
			User user = ((User) authentication.getPrincipal());
			if (user.getId() != null && 1L == user.getId()) {
				logger.debug("登录用户管理员,执行操作" + perStr);
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		logger.debug("用户:" + ((User) authentication.getPrincipal()).getUsername() + "执行操作：" + perStr);
		Collection<? extends GrantedAuthority> as = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : as) {
			// 判断列表权限 只要有任何一个该模型的操作权限 列表权限都返回true
			if (isPagePerStr(operate)) {
				if (grantedAuthority.getAuthority().startsWith(domain.toUpperCase())) {
					logger.debug("权限验证通过");
					return true;
				}
			}
			if (grantedAuthority.getAuthority().equals(perStr)) {
				logger.debug("权限验证通过");
				return true;
			}
		}
		logger.debug("权限验证失败");
		return false;
	}

	private String getPermissionStr(String domain, String operate) {
		if (operate.equalsIgnoreCase("tree"))
			operate = "page";
		return domain.toUpperCase() + "_" + operate.toUpperCase();
	}

	private boolean isPagePerStr(String operate) {
		if (StringUtils.isEmpty(operate))
			return false;
		if (operate.equalsIgnoreCase("page") || operate.equalsIgnoreCase("tree"))
			return true;
		return false;
	}

}
