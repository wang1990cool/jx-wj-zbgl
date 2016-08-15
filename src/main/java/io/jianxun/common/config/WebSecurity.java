package io.jianxun.common.config;

import org.springframework.security.core.Authentication;

public class WebSecurity {
	/**
	 * 
	 * 通过url进行权限判断的统一入口
	 * 
	 * @param authentication 
	 * @param domain  实体对象
	 * @param operate 操作
	 * @return
	 */
	public boolean check(Authentication authentication, String domain, String operate) {
		return false;
	}

}
