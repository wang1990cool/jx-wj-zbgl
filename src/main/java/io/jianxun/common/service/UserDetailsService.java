package io.jianxun.common.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.common.domain.user.UserDetails;
import io.jianxun.common.repository.user.UserRepository;
import io.jianxun.common.service.exception.ServiceException;

@Service
@Transactional(readOnly = true)
public class UserDetailsService extends EntityService<UserDetails>
		implements org.springframework.security.core.userdetails.UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	@Transactional(readOnly = false)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = null;
		try {
			List<UserDetails> users = userRepository.findAll();
			if (users.isEmpty()) {
				UserDetails admin = new UserDetails();
				admin.setName("管理员");
				admin.setUsername("admin");
				admin.setPassword("admin");
				register(admin);
				flush();
			}

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
		// 用户失效
		if (!user.isAccountNonExpired()) {
			logger.debug("user is expired | loginname : %s", username);
			throw new UsernameNotFoundException("user is expired");
		}
		// 用户锁定
		if (!user.isAccountNonLocked()) {
			logger.debug("user is locked | loginname : %s", username);
			throw new UsernameNotFoundException("user is locked");
		}
		// 密码失效
		if (!user.isCredentialsNonExpired()) {
			logger.debug("user credentials is expired | loginname : %s", username);
			throw new UsernameNotFoundException("user credentials is expired");
		}
		// 用户不可用
		if (!user.isEnabled()) {
			logger.debug("user not enabled | loginname : %s", username);
			throw new UsernameNotFoundException("user not enabled");
		}

		return user;
	}

	@Override
	@Transactional(readOnly = false)
	public UserDetails save(UserDetails entity) {
		if (userRepository.isNew(entity))
			return register(entity);
		if (entity.getId() != null && isAdmin(entity))
			throw new ServiceException("超级管理员用户不能修改");
		return super.save(entity);
	}

	public boolean isAdmin(UserDetails entity) {
		return 1L == entity.getId();
	}

	/*
	 * 用户注册 新注册用户进行密码加密
	 */
	@Transactional(readOnly = false)
	public UserDetails register(UserDetails user) {
		if (userRepository.isNew(user)) {
			logger.info("用户注册");
			// 密码加密
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

			return super.save(user);
		}
		logger.error("注册操作失败，用户id %d 已经存在", user.getId());
		throw new ServiceException(String.format("user %s is registered,can not register again!", user.getUsername()));
	}

	/*
	 * 修改密码
	 */
	@Transactional(readOnly = false)
	public UserDetails changePassword(UserDetails user, String newPassword) {
		logger.info("[当前用户 %s] -- 修改%s用户密码 -- ", getCurrentUser(), user);
		user.setPassword(bCryptPasswordEncoder.encode(newPassword));
		return save(user);
	}

	/*
	 * 用户失效设置
	 */
	@Transactional(readOnly = false)
	public UserDetails accountExpired(UserDetails user, boolean accountExpired) {
		logger.info("[当前用户 %s] -- 修改%s用户失效状态 %b -- ", getCurrentUser(), user, accountExpired);
		user.setAccountNonExpired(!accountExpired);
		return save(user);
	}

	/*
	 * 用户锁定设置
	 */
	@Transactional(readOnly = false)
	public UserDetails accountLocked(UserDetails user, boolean accountLocked) {
		logger.info("[当前用户 %s] -- 修改%s用户锁定状态 %b -- ", getCurrentUser(), user, accountLocked);
		user.setAccountNonLocked(!accountLocked);
		return save(user);
	}

	/*
	 * 密码失效设置
	 */
	@Transactional(readOnly = false)
	public UserDetails credentialsExpired(UserDetails user, boolean credentialsExpired) {
		logger.info("[当前用户 %s] -- 修改%s用户密码失效状态 %b -- ", getCurrentUser(), user, credentialsExpired);
		user.setCredentialsNonExpired(!credentialsExpired);
		return save(user);
	}

	/*
	 * 用户可用设置
	 */
	@Transactional(readOnly = false)
	public UserDetails enabled(UserDetails user, boolean enabled) {
		logger.info("[当前用户 %s] -- 修改%s用户可用状态 %b -- ", getCurrentUser(), user, enabled);
		user.setEnabled(enabled);
		return save(user);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(UserDetails entity) {
		enabled(entity, false);
	}

	public boolean validateUsernameIsUnique(String username, Long id) {
		Long count = 0L;
		if (id != null && id != -1)
			count = userRepository.countByUsernameAndIdNotAndEnabled(username, id, true);
		else
			count = userRepository.countByUsernameAndEnabled(username, true);
		return count == 0;
	}

}
