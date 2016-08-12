package io.jianxun.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.jianxun.common.domain.user.User;
import io.jianxun.common.domain.user.UserDetails;
import io.jianxun.common.repository.user.UserRepository;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
		User user = null;
		try {
			user = userRepository.findByLoginName(loginName);
		} catch (Exception e) {
			logger.debug("db excute error,user found faill | loginname : %s", loginName);
			e.printStackTrace();
			throw new UsernameNotFoundException("db excute error,user found fail");
		}
		if (user == null) {
			logger.debug("user not found | loginname : %s", loginName);
			throw new UsernameNotFoundException("user not found");
		}
		UserDetails userDetails = new UserDetails();
		userDetails.setUser(user);
		return userDetails;
	}

}
