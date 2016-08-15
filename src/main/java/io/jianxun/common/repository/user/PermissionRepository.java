package io.jianxun.common.repository.user;

import java.util.List;

import io.jianxun.common.domain.user.Permission;
import io.jianxun.common.repository.EntityRepository;

public interface PermissionRepository extends EntityRepository<Permission, Long> {
	
	List<Permission> findByOwner(Long roleId);

}
