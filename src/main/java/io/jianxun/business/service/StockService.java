package io.jianxun.business.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.Department;
import io.jianxun.business.domain.Weapon;
import io.jianxun.business.domain.stock.Stock;
import io.jianxun.business.domain.stock.StockIn;
import io.jianxun.business.domain.stock.StockInDetail;
import io.jianxun.business.event.StockMinMaxEvent;
import io.jianxun.business.repository.StockRepository;
import io.jianxun.common.service.exception.ServiceException;

@Service
@Transactional(readOnly = true)
public class StockService extends DepartmentableService<Stock> {

	static final String MSG = "总库存%d";
	static final String SUBMSG = " | 机构[%s],库存%d";

	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private StockInDetailService stockInDetailService;

	private final ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	public StockService(ApplicationEventPublisher applicationEventPublisher) {
		super();
		this.applicationEventPublisher = applicationEventPublisher;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.jianxun.business.service.BusinessBaseEntityService#save(io.jianxun.
	 * business.domain.BusinessBaseEntity)
	 */
	@Override
	public Stock save(Stock stock) {
		sendMinMaxEvent(stock);
		return super.save(stock);
	}

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
		sb.append(String.format(MSG, all));
		List<Department> subDep = departmentService.findSubDepart(depart);
		if (subDep.size() > 0) {
			for (Department department : subDep) {
				Long subAll = countByDepartAndWeapon(department, weapon);
				if (subAll != null && subAll > 0)
					sb.append(String.format(SUBMSG, department.getDisplayName(), subAll));
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

	private void sendMinMaxEvent(Stock stock) {
		Integer min = stock.getMinInventory();
		Integer max = stock.getMaxInventory();
		if (min != -1 || max != -1) {
			// 跟新库存提醒
			StockMinMaxEvent event = new StockMinMaxEvent();
			event.setStock(stock);
			applicationEventPublisher.publishEvent(event);
		}
	}

	public Stock findByWeapon(Department depart, Weapon weapon) {
		Stock stock = ((StockRepository) this.entityRepository).findByDepartAndWeapon(depart, weapon);
		if (stock != null)
			addStockInfo(stock);
		return stock;
	}

	// 移库
	@Transactional(readOnly = false)
	public void refrashStock(Department source, Department destination, Weapon weapon, Set<StockInDetail> details) {
		if (source.getId() == destination.getId())
			throw new ServiceException("机构内部不能进行调整操作");
		Stock s = findByWeapon(source, weapon);
		if (s == null)
			throw new ServiceException("获取机构库存信息失败");
		if (s.getInventory() < details.size())
			throw new ServiceException("机构库存量不足,操作失败");
		s.setInventory(s.getInventory() - details.size());
		Stock d = findByWeapon(destination, weapon);
		if (d != null) {
			Integer inventory = d.getInventory();
			d.setInventory(inventory + details.size());
		} else {
			d = new Stock();
			d.setWeapon(weapon);
			d.setDepart(destination);
			d.setInventory(details.size());
		}
		save(s);
		save(d);
		stockInDetailService.moveDetail(details, destination);

	}

	@Transactional(readOnly = false)
	public void settingMinAndMax(Stock stock, Integer minInventory, Integer maxInventory) {
		if (minInventory > 0)
			stock.setMinInventory(getMin(minInventory, maxInventory));
		else
			stock.setMinInventory(-1);
		if (maxInventory > 0)
			stock.setMaxInventory(getMax(minInventory, maxInventory));
		else
			stock.setMaxInventory(-1);
		if (minInventory > 0 || maxInventory > 0) {
			save(stock);
		}
	}

	private Integer getMax(Integer minInventory, Integer maxInventory) {
		return minInventory < maxInventory ? maxInventory : minInventory;
	}

	private Integer getMin(Integer minInventory, Integer maxInventory) {
		if(maxInventory<0)
			return minInventory;
		return minInventory < maxInventory ? minInventory : maxInventory;
	}

}
