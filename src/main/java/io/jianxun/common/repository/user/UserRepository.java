package io.jianxun.common.repository.user;

import io.jianxun.common.domain.user.User;
import io.jianxun.common.repository.EntityRepository;

public interface UserRepository extends EntityRepository<User, Long> {

	User findByLoginName(String loginName);

}
