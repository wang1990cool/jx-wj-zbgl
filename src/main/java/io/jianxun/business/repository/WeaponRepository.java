package io.jianxun.business.repository;

import io.jianxun.business.domain.Weapon;
import io.jianxun.common.repository.EntityRepository;

public interface WeaponRepository extends EntityRepository<Weapon, Long> {

	long countByName(String name);

	long countByNameAndId(String name, Long id);

}
