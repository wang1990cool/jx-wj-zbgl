package io.jianxun.common.repository.user;

import io.jianxun.common.domain.user.UserDetails;
import io.jianxun.common.repository.EntityRepository;

public interface UserRepository extends EntityRepository<UserDetails, Long> {

	UserDetails findByUsername(String username);

}
