package io.jianxun.business.repository;

import io.jianxun.business.domain.StockNotice;
import io.jianxun.business.domain.stock.Stock;
import io.jianxun.business.enums.BooleanStatus;

public interface StockNoticeRepository extends BusinessBaseRepository<StockNotice> {

	StockNotice findByStockAndDeleted(Stock stock, BooleanStatus deleted);

}
