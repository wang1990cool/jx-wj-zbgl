package io.jianxun.business.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.Department;
import io.jianxun.business.domain.Weapon;
import io.jianxun.business.domain.stock.Stock;
import io.jianxun.business.domain.stock.StockIn;
import io.jianxun.business.repository.StockRepository;
import io.jianxun.common.service.exception.ServiceException;

@Service
@Transactional(readOnly = true)
public class StockService extends DepartmentableService<Stock> {

	static final String MSG = "当前机构[%s],总库存%d";
	static final String SUBMSG = " | 机构[%s],库存%d";

	@Autowired
	private DepartmentService departmentService;

	@Override
	public Page<Stock> findAll(Pageable pageable, Map<String, Object> searchParams) {
		Page<Stock> page = super.findAll(pageable, searchParams);
		addStockInfo(page.getContent());
		return page;
	}

	private void addStockInfo(List<Stock> content) {
		for (Stock stock : content) {
			addStockInfo(stock);

		}
	}

	private void addStockInfo(Stock stock) {
		Long total = getWeaponTotal(stock.getWeapon(), stock.getDepart());
		stock.setTotal(total);
		String des = getWeaponassignment(stock.getWeapon(), stock.getDepart(), total);
		stock.setDescription(des);
	}

	private Long getWeaponTotal(Weapon weapon, Department depart) {
		return countByDepartLevelCodeStartingWithAndWeapon(depart.getLevelCode(), weapon);
	}

	// 装备在机构及以下机构分配情况
	public String getWeaponassignment(Weapon weapon, Department depart, Long all) {
		// 机构及以下装备总量其中当前机构库存多少,下级机构库存多少
		StringBuilder sb = new StringBuilder(50);
		sb.append(String.format(MSG, depart.getName(), all));
		List<Department> subDep = departmentService.findSubDepart(depart);
		if (subDep.size() > 0) {
			sb.append("其中");
			for (Department department : subDep) {
				Long subAll = countByDepartAndWeapon(department, weapon);
				if (subAll != null && subAll > 0)
					sb.append(String.format(SUBMSG, department.getName(), subAll));
			}

		}
		return sb.toString();
	}

	// 获取机构及以下装备总量
	public Long countByDepartLevelCodeStartingWithAndWeapon(String levelCode, Weapon weapon) {
		return ((StockRepository) this.entityRepository).sumByDepartLevelCodeStartingWithAndWeapon(levelCode + "%",
				weapon);
	}

	// 获取机构下装备数量
	public Long countByDepartAndWeapon(Department depart, Weapon weapon) {
		return ((StockRepository) this.entityRepository).sumByDepartAndWeapon(depart, weapon);
	}

	// 获取上级机构各类装备库存情况
	public List<Stock> getParentDepartStock(Department currentDepart) {
		Department parent = departmentService.findOne(currentDepart.getpId());
		if (parent == null)
			throw new ServiceException("获取上级机构信息失败,无法获取库存信息");
		List<Stock> result = ((StockRepository) this.entityRepository).findByDepart(parent);
		addStockInfo(result);
		return result;

	}

	@Transactional(readOnly = false)
	public void refrashStock(StockIn stockIn) {
		Stock stock = findByWeapon(stockIn.getDepart(), stockIn.getWeapon());
		if (stock != null) {

			// TODO 库存更新 思路当日更新 待优化
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

	public Stock findByWeapon(Department depart, Weapon weapon) {
		Stock stock = ((StockRepository) this.entityRepository).findByDepartAndWeapon(depart, weapon);
		if (stock != null)
			addStockInfo(stock);
		return stock;
	}

}
