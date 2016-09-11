package io.jianxun.business.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.jianxun.business.domain.stock.Stock;
import io.jianxun.business.domain.stock.StockInDetail;
import io.jianxun.business.service.StockService;
import io.jianxun.common.service.EntityService;
import io.jianxun.common.utils.Servlets;
import io.jianxun.common.web.EntityController;

@Controller
@RequestMapping("business/stockindetail")
public class StockInDetailController extends EntityController<StockInDetail, Long> {

	public StockInDetailController(EntityService<StockInDetail, Long> entityService) {
		super(entityService);
	}

	@RequestMapping(value = { "/page/{stock}" })
	public String pageWithPid(Model model, Pageable pageable, @PathVariable("stock") Long stockId,
			@RequestParam(value = "orderField", defaultValue = "id") String orderField,
			@RequestParam(value = "orderDirection", defaultValue = "DESC") String orderDirection) {
		Sort sort = createSort(orderField, orderDirection);
		Stock stock = stockService.findOne(stockId);
		Map<String, Object> searchParams = getSearchParam();
		searchParams.put("STARTWITH_stockIn.depart.levelCode", stock.getDepart().getLevelCode());
		searchParams.put("EQ_stockIn.weapon.id", Long.toString(stock.getWeapon().getId()));
		Page<StockInDetail> page = entityService.findAll(buildPageable(pageable, sort), searchParams);
		model.addAttribute("content", page.getContent());
		model.addAttribute("page", page.getNumber() + 1);
		model.addAttribute("size", page.getSize());
		model.addAttribute("orderField", orderField);
		model.addAttribute("orderDirection", orderDirection);
		model.addAttribute("total", page.getTotalElements());
		model.addAttribute("dId", stockId);
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		return getTemplePrefix() + "/page";
	}

	@Autowired
	private StockService stockService;

}
