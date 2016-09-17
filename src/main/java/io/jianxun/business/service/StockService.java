package io.jianxun.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.Weapon;
import io.jianxun.business.domain.stock.Stock;
import io.jianxun.business.domain.stock.StockIn;
import io.jianxun.business.repository.StockRepository;

@Service
@Transactional(readOnly = true)
public class StockService extends DepartmentableService<Stock> {
	@Transactional(readOnly = false)
	public void refrashStock(StockIn stockIn) {
		Stock stock = findByWeapon(stockIn.getWeapon());
		if (stock != null) {

			// TODO 库存更新 思路当日更新
			Integer inventory = stock.getInventory();
			stock.setInventory(inventory + stockIn.getCapacity());
		} else {
			stock = new Stock();
			stock.setWeapon(stockIn.getWeapon());
			stock.setDepart(stockIn.getDepart());
			stock.setInventory(stockIn.getCapacity());
		}
		save(stock);
	}

	public Stock findByWeapon(Weapon weapon) {
		return ((StockRepository) this.entityRepository).findByWeapon(weapon);
	}

}
