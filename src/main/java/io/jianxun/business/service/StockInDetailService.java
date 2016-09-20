package io.jianxun.business.service;

import java.text.DecimalFormat;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

import io.jianxun.business.domain.stock.StockIn;
import io.jianxun.business.domain.stock.StockInDetail;
import io.jianxun.business.repository.StockInDetailRepository;
import io.jianxun.common.service.EntityService;

@Service
@Transactional(readOnly = true)
public class StockInDetailService extends EntityService<StockInDetail> {

	public String getMaxSno(String prefix) {
		StockInDetail detail = ((StockInDetailRepository) entityRepository)
				.findTopByStockCodePrefixOrderBySNoDesc(prefix);
		if (detail != null)
			return detail.getsNo();
		return "";
	}

	public String getNextSno(String prefix, int i) {
		String currentMax = getMaxSno(prefix);
		DecimalFormat format = new DecimalFormat("0000");
		if (StringUtils.isEmpty(currentMax))
			currentMax = "0001";
		try {
			int current = format.parse(currentMax).intValue();
			return format.format(current + i);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("获取库存明细流水号错误！");
		}
	}

	@Transactional(readOnly = false)
	public void save(StockIn stockIn) {
		List<StockInDetail> details = Lists.newArrayList();
		details = convert2Details(stockIn);
		save(details);

	}

	private List<StockInDetail> convert2Details(StockIn stockIn) {
		List<StockInDetail> details = Lists.newArrayList();
		String weaponCodes = stockIn.getWeaponCodes();
		String[] wcs = org.apache.commons.lang3.StringUtils.split(weaponCodes, ",");
		int capacity = stockIn.getCapacity();
		for (int i = 0; i < capacity; i++) {
			String ownCode = null;
			if (i < wcs.length)
				ownCode = wcs[i];
			details.add(createStockInDetail(stockIn, ownCode, i));

		}
		return details;
	}

	private StockInDetail createStockInDetail(StockIn stockIn, String ownCode, int i) {
		StockInDetail detail = new StockInDetail();
		detail.setStockIn(stockIn);
		if (!StringUtils.isEmpty(ownCode))
			detail.setOwnCode(ownCode);
		if (StringUtils.isEmpty(detail.getStockCodePrefix()))
			throw new ServiceException("获取装备流水号出错！");
		detail.setsNo(getNextSno(detail.getStockCodePrefix(), i));
		return detail;

	}

}