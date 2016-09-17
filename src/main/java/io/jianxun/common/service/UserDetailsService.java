package io.jianxun.common.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import io.jianxun.common.domain.user.Permission;
import io.jianxun.common.domain.user.Role;
import io.jianxun.common.domain.user.UserDetails;
import io.jianxun.common.repository.user.PermissionRepository;
import io.jianxun.common.repository.user.RoleRepository;
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
	private RoleRepository roleRepository;
	@Autowired
	private PermissionRepository permissionRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	@Transactional(readOnly=false)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = null;
		try {
			List<UserDetails> users = userRepository.findAll();
			if(users.isEmpty()){
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
		// 获取用户权限信息
		List<Role> roles = roleRepository.findByOwner(user.getId());
		if (roles != null && roles.size() != 0) {
			List<String> result = Lists.newArrayList();
			for (Role role : roles) {
				List<Permission> permissions = permissionRepository.findByOwner(role.getId());
				if (permissions == null || permissions.size() < 1) {
					continue;
				}
				for (Permission permission : permissions) {
					result.add(permission.getValue());
				}
			}
			user.setPermissions(result);
		}
		
		return user;
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
		return super.save(user);
	}

	/*
	 * 用户失效设置
	 */
	@Transactional(readOnly = false)
	public UserDetails accountExpired(UserDetails user, boolean accountExpired) {
		logger.info("[当前用户 %s] -- 修改%s用户失效状态 %b -- ", getCurrentUser(), user, accountExpired);
		user.setAccountNonExpired(!accountExpired);
		return super.save(user);
	}

	/*
	 * 用户锁定设置
	 */
	@Transactional(readOnly = false)
	public UserDetails accountLocked(UserDetails user, boolean accountLocked) {
		logger.info("[当前用户 %s] -- 修改%s用户锁定状态 %b -- ", getCurrentUser(), user, accountLocked);
		user.setAccountNonLocked(!accountLocked);
		return super.save(user);
	}

	/*
	 * 密码失效设置
	 */
	@Transactional(readOnly = false)
	public UserDetails credentialsExpired(UserDetails user, boolean credentialsExpired) {
		logger.info("[当前用户 %s] -- 修改%s用户密码失效状态 %b -- ", getCurrentUser(), user, credentialsExpired);
		user.setCredentialsNonExpired(!credentialsExpired);
		return super.save(user);
	}

	/*
	 * 用户可用设置
	 */
	@Transactional(readOnly = false)
	public UserDetails enabled(UserDetails user, boolean enabled) {
		logger.info("[当前用户 %s] -- 修改%s用户可用状态 %b -- ", getCurrentUser(), user, enabled);
		user.setEnabled(enabled);
		return super.save(user);
	}

}
