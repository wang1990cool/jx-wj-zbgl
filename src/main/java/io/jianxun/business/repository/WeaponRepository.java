package io.jianxun.business.repository;

import io.jianxun.business.domain.DataDictionary;
import io.jianxun.business.domain.Weapon;
import io.jianxun.business.enums.BooleanStatus;

public interface WeaponRepository extends BusinessBaseRepository<Weapon> {

	long countByName(String name);

	long countByNameAndId(String name, Long id);

	Weapon findTopByNameAndCategoryOrderByTypeCodeDesc(String name, DataDictionary category);

	Weapon findTopByOrderByCodeDesc();

	Long countByNameAndTypeAndIdNotAndDeleted(String name, String type, Long id, BooleanStatus deleted);

	Long countByNameAndTypeAndDeleted(String name, String type, BooleanStatus deleted);

	Long countByNameAndIdNotAndDeleted(String name, Long id, BooleanStatus deleted);

	Long countByNameAndDeleted(String name, BooleanStatus deleted);

}
