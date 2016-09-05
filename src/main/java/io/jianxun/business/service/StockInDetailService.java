package io.jianxun.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.stock.StockIn;
import io.jianxun.business.domain.stock.StockInDetail;
import io.jianxun.common.service.EntityService;

@Service
@Transactional(readOnly = true)
public class StockInDetailService extends EntityService<StockInDetail, Long> {

	@Transactional(readOnly = false)
	public void save(StockIn stock) {

	}

}
