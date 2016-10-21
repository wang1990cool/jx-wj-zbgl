package io.jianxun.business.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.jianxun.business.domain.Department;
import io.jianxun.business.domain.stock.Stock;
import io.jianxun.business.service.DepartmentService;
import io.jianxun.business.service.DepartmentableService;
import io.jianxun.business.service.StockService;
import io.jianxun.business.web.dto.ReturnDto;
import io.jianxun.business.web.dto.StockSettingDto;
import io.jianxun.common.service.exception.ServiceException;

@Controller
@RequestMapping("business/stock")
public class StockController extends DepartmentableController<Stock> {

	@Autowired
	private DepartmentService departmentService;

	public StockController(DepartmentableService<Stock> entityService) {
		super(entityService);
	}

	@Override
	protected void addDepartSearchParam(Map<String, Object> searchParams, Department depart) {
		searchParams.put("EQ_depart.levelCode", depart.getLevelCode());
	}

	@RequestMapping(value = "/ajax/search/weapon/{currentDeparrId}")
	public String getParentDepartStock(@PathVariable("currentDeparrId") Long pId, Model model) {
		Department currentDepart = departmentService.findOne(pId);
		if (currentDepart == null)
			throw new ServiceException("获取机构信息失败,无法获取对应库存");
		if (departmentService.isRoot(currentDepart))
			throw new ServiceException("当前机构为主库机构，无法获取对应库存");
		model.addAttribute("content", ((StockService) entityService).getParentDepartStock(currentDepart));
		return "stock/weaponsearch";

	}

	@RequestMapping(value = "/setting/{currentStockId}", method = RequestMethod.GET)
	public String stocksetform(@PathVariable("currentStockId") Long stockid, Model model) {
		model.addAttribute("d", new StockSettingDto(stockid, -1, -1));
		return "stock/settingform";
	}

	@RequestMapping(value = "/setting", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDto stockset(StockSettingDto stockSetting) {
		if (stockSetting == null)
			throw new ServiceException("设置失败，获取数据出错");
		Long stockId = stockSetting.getStockId();
		Stock stock = this.entityService.findOne(stockId);
		if (stock == null)
			throw new ServiceException("获取库存信息失败");
		((StockService) this.entityService).settingMinAndMax(stock, stockSetting.getMinInventory(),
				stockSetting.getMaxInventory());
		return ReturnDto.ok("设置成功");
	}

}
