package io.jianxun.business.repository;

import io.jianxun.business.domain.Weapon;
import io.jianxun.business.domain.stock.Stock;
import io.jianxun.common.repository.EntityRepository;

public interface StockRepository extends EntityRepository<Stock, Long> {

	Stock findByWeapon(Weapon weapon);

}
