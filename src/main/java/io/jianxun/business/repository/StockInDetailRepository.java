package io.jianxun.business.repository;

import java.time.LocalDate;
import java.util.List;

import io.jianxun.business.domain.stock.StockInDetail;
import io.jianxun.common.repository.EntityRepository;

public interface StockInDetailRepository extends EntityRepository<StockInDetail, Long> {

	StockInDetail findTopByStockCodePrefixOrderBySNoDesc(String stockCode);

	List<StockInDetail> findByMaintenanceDateBefore(LocalDate maintenanceDate);

}
