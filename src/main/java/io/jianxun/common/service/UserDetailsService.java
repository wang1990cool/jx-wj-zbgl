package io.jianxun.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.common.domain.user.UserDetails;
import io.jianxun.common.repository.EntityRepositoryImpl;
import io.jianxun.common.repository.user.UserRepository;
import io.jianxun.common.service.exception.ServiceException;

@Service
@Transactional(readOnly = true)
public class UserDetailsService extends EntityService<UserDetails, Long>
		implements org.springframework.security.core.userdetails.UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = null;
		try {
			user = userRepository.findByUsername(username);
		} catch (Exception e) {
			logger.debug("db excute error,user found faill | loginname : %s", username);
			e.printStackTrace();
			throw new UsernameNotFoundException("db excute error,user found fail");
		}
		if (user == null) {
			logger.debug("user not found | loginname : %s", username);
			throw new UsernameNotFoundException("user not found");
		}
		return user;
	}

	/*
	 * 用户注册 新注册用户进行密码加密
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public UserDetails register(UserDetails user) {
		if (((EntityRepositoryImpl<UserDetails, Long>) userRepository).isNew(user)) {
			logger.info("用户注册");
			// 密码加密
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

			return super.save(user);
		}
		logger.error("注册操作失败，用户id %d 已经存在", user.getId());
		throw new ServiceException(
				String.format("user %s is registered,can not register again!", user.getUsername()));
	}

	/*
	 * 重置密码
	 */
	@Transactional(readOnly = false)
	public UserDetails resetPassword(UserDetails user, String newPassword) {
		logger.info("[当前用户 %s] -- 重置%s用户密码 -- ", getCurrentUser(), user);
		user.setPassword(bCryptPasswordEncoder.encode(newPassword));
		return super.save(user);
	}

	/*
	 * 用户失效设置
	 */
	@Transactional(readOnly = false)
	public UserDetails accountExpired(UserDetails user, boolean accountExpired) {
		logger.info("[当前用户 %s] -- 修改%s用户失效状态 %b -- ", getCurrentUser(), user,accountExpired);
		user.setAccountNonExpired(!accountExpired);
		return super.save(user);
	}

	/*
	 * 用户锁定设置
	 */
	@Transactional(readOnly = false)
	public UserDetails accountLocked(UserDetails user, boolean accountLocked) {
		logger.info("[当前用户 %s] -- 修改%s用户锁定状态 %b -- ", getCurrentUser(), user,accountLocked);
		user.setAccountNonLocked(!accountLocked);
		return super.save(user);
	}

	/*
	 * 密码失效设置
	 */
	@Transactional(readOnly = false)
	public UserDetails credentialsExpired(UserDetails user, boolean credentialsExpired) {
		logger.info("[当前用户 %s] -- 修改%s用户密码失效状态 %b -- ", getCurrentUser(), user,credentialsExpired);
		user.setCredentialsNonExpired(!credentialsExpired);
		return super.save(user);
	}

	/*
	 * 用户可用设置
	 */
	@Transactional(readOnly = false)
	public UserDetails enabled(UserDetails user, boolean enabled) {
		logger.info("[当前用户 %s] -- 修改%s用户可用状态 %b -- ", getCurrentUser(), user,enabled);
		user.setEnabled(enabled);
		return super.save(user);
	}

}
