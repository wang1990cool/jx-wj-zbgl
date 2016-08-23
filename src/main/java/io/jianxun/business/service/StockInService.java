package io.jianxun.business.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.stock.StockIn;

@Service
@Transactional(readOnly = true)
public class StockInService extends DepartmentableService<StockIn, Long> {

	@Autowired
	private StockService stockService;

	@Override
	@Transactional(readOnly = false)
	public StockIn save(StockIn stock) {
		if (this.entityRepository.isNew(stock)) {
			stock.setCreateDate(LocalDate.now());
			stock.setCreateUser(getCurrentUser());
		}
		// 更新库存
		stockService.refrashStock(stock);
		return super.save(stock);

	}

}
