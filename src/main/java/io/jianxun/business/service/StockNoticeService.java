package io.jianxun.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.StockNotice;
import io.jianxun.business.domain.stock.Stock;
import io.jianxun.business.enums.BooleanStatus;
import io.jianxun.business.repository.StockNoticeRepository;

@Service
@Transactional(readOnly = true)
public class StockNoticeService extends DepartmentableService<StockNotice> {

	// 库存上下限提醒
	@Transactional(readOnly = false)
	public void createNotice(Stock stock) {
		StockNotice notice = findByStock(stock);
		if (stock.getInventory() >= stock.getMinInventory() && stock.getInventory() <= stock.getMaxInventory()) {
			if (notice != null) {
				delete(notice);
				return;
			}
		}
		if (notice == null||isDeleted(notice))
			notice = new StockNotice();
		if (stock.getInventory() < stock.getMinInventory() || stock.getInventory() > stock.getMaxInventory())
			createNoiceMessage(stock, notice);
		save(notice);
	}

	public StockNotice findByStock(Stock stock) {
		return ((StockNoticeRepository) this.entityRepository).findByStockAndDeleted(stock,BooleanStatus.False);
	}

	private void createNoiceMessage(Stock stock, StockNotice notice) {
		notice.setDepart(stock.getDepart());
		notice.setStock(stock);
		notice.setMessage(String.format("装备 %s 当前库存量 [%d] 超出库存限制%d-%d", stock.getWeapon(), stock.getInventory(),
				stock.getMinInventory(), stock.getMaxInventory()));
	}

}
