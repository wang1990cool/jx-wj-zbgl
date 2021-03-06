package io.jianxun.business.service;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import io.jianxun.business.domain.User;
import io.jianxun.common.domain.user.UserDetails;

public class AuditorAwareImpl implements AuditorAware<UserDetails> {

	@Override
	public User getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}

		return ((User) authentication.getPrincipal());
	}

}
