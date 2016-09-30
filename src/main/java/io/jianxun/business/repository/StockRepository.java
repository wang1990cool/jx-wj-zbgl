package io.jianxun.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.jianxun.business.domain.Department;
import io.jianxun.business.domain.Weapon;
import io.jianxun.business.domain.stock.Stock;
import io.jianxun.common.repository.EntityRepository;

public interface StockRepository extends EntityRepository<Stock, Long> {

	Stock findByDepartAndWeapon(Department depart, Weapon weapon);

	@Query("select sum(s.inventory) from Stock as s where s.depart.levelCode like :levelCode and s.weapon= :weapon")
	Long sumByDepartLevelCodeStartingWithAndWeapon(@Param("levelCode") String levelCode,
			@Param("weapon") Weapon weapon);

	@Query("select sum(s.inventory) from Stock as s where s.depart = :depart and s.weapon= :weapon")
	Long sumByDepartAndWeapon(@Param("depart") Department depart, @Param("weapon") Weapon weapon);

	List<Stock> findByDepart(Department parent);

}
