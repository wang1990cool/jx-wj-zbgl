package io.jianxun.business.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.Department;
import io.jianxun.business.domain.stock.StockIn;
import io.jianxun.business.enums.StockInCategory;

@Service
@Transactional(readOnly = true)
public class StockInService extends DepartmentableService<StockIn> {

	@Autowired
	private StockService stockService;
	@Autowired
	private StockInDetailService stockInDetailService;

	@Override
	@Transactional(readOnly = false)
	public StockIn save(StockIn stock) {
		if (this.entityRepository.isNew(stock)) {
			stock.setCreateDate(LocalDate.now());
			stock.setCreateUser(getCurrentUser());
		}
		// 判断是否时总库入库
		Department d = stock.getDepart();
		if (1L != d.getId())
			stock.setStockInCategory(StockInCategory.NO_ROOT.getCode());
		StockIn si = super.save(stock);
		this.entityRepository.flush();
		// 更新库存详表，此处可用事件发布优化
		stockInDetailService.save(si);
		// 更新库存数量表
		stockService.refrashStock(si);
		return si;

	}

}
