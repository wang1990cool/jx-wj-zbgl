package io.jianxun.business.repository;

import io.jianxun.business.domain.Weapon;
import io.jianxun.business.domain.stock.StockIn;
import io.jianxun.business.enums.StockInCategory;
import io.jianxun.common.repository.EntityRepository;

public interface StockInRepository extends EntityRepository<StockIn, Long> {

	Long countByWeaponAndStockInCategory(Weapon weapon, StockInCategory category);

}
