package io.jianxun.business.repository;

import java.time.LocalDate;
import java.util.List;

import io.jianxun.business.domain.Department;
import io.jianxun.business.domain.Weapon;
import io.jianxun.business.domain.stock.StockInDetail;
import io.jianxun.common.repository.EntityRepository;

public interface StockInDetailRepository extends EntityRepository<StockInDetail, Long> {

	StockInDetail findTopByStockCodePrefixOrderBySNoDesc(String stockCode);

	List<StockInDetail> findByMaintenanceDateBefore(LocalDate maintenanceDate);

	List<StockInDetail> findByDepartAndStockInWeapon(Department department, Weapon weapon);

	List<StockInDetail> findByStatusAndMaintenanceNoticeDateBefore(String status, LocalDate noticeDate);

	List<StockInDetail> findByStatusAndRetirementPeriodNoticeDateBefore(String status, LocalDate noticeDate);

	List<StockInDetail> findByStockInWeapon(Weapon weapon);

}
