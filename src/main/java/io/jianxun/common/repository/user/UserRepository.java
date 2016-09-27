package io.jianxun.common.repository.user;

import io.jianxun.business.domain.Constable;
import io.jianxun.business.domain.User;
import io.jianxun.common.domain.user.UserDetails;
import io.jianxun.common.repository.EntityRepository;

public interface UserRepository extends EntityRepository<User, Long> {

	UserDetails findByUsername(String username);

	Long countByUsernameAndIdNotAndEnabled(String username, Long id, boolean enabled);

	Long countByUsernameAndEnabled(String username, boolean enabled);

	Long countByConstableAndIdNotAndEnabled(Constable constable, Long id, boolean b);

	Long countByConstableAndEnabled(Constable constable, boolean b);

}
