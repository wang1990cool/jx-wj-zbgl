package io.jianxun.common.repository.user;

import java.util.List;

import io.jianxun.common.domain.user.Role;
import io.jianxun.common.repository.EntityRepository;

public interface RoleRepository extends EntityRepository<Role, Long> {

	List<Role> findByOwner(Long userId);

}
