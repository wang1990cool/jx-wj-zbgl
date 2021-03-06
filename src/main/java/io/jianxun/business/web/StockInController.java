package io.jianxun.business.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.jianxun.business.domain.Weapon;
import io.jianxun.business.domain.editor.WeaponEditor;
import io.jianxun.business.domain.stock.Stock;
import io.jianxun.business.domain.stock.StockIn;
import io.jianxun.business.domain.validator.StockInValidator;
import io.jianxun.business.enums.StockInCategory;
import io.jianxun.business.service.DepartmentableService;
import io.jianxun.business.service.StockService;
import io.jianxun.business.service.WeaponService;
import io.jianxun.business.web.dto.ReturnDto;
import io.jianxun.common.utils.Servlets;

@Controller
@RequestMapping("business/stockin")
public class StockInController extends DepartmentableController<StockIn> {

	@Autowired
	private WeaponService weaponService;
	@Autowired
	private StockService stockService;

	public StockInController(DepartmentableService<StockIn> entityService) {
		super(entityService);
	}

	@Override
	@RequestMapping(value = { "/page/{stock}" })
	public String pageWithPid(Model model, Pageable pageable, @PathVariable("stock") Long stockId,
			@RequestParam(value = "orderField", defaultValue = "id") String orderField,
			@RequestParam(value = "orderDirection", defaultValue = "DESC") String orderDirection) {
		Sort sort = createSort(orderField, orderDirection);
		Stock stock = stockService.findOne(stockId);
		Map<String, Object> searchParams = getSearchParam();
		searchParams.put("STARTWITH_depart.levelCode", stock.getDepart().getLevelCode() + "%");
		searchParams.put("EQ_weapon.id", Long.toString(stock.getWeapon().getId()));
		Page<StockIn> page = entityService.findAll(buildPageable(pageable, sort), searchParams);
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

	@Override
	protected void prepareCreateForm(Model model) {
		super.prepareCreateForm(model);
		model.addAttribute("categories", StockInCategory.values());
		model.addAttribute("weapons", weaponService.getSelectData(weaponService.findAll()));
	}

	@Override
	protected void prepareModifyForm(Model model) {
		super.prepareModifyForm(model);
		model.addAttribute("categories", StockInCategory.values());
		model.addAttribute("weapons", weaponService.getSelectData(weaponService.findAll()));
	}

	@Override
	protected ReturnDto getSaveReturn() {
		return ReturnDto.tabSuccessReturn("操作成功!", "stock-page");
	}

	@InitBinder
	public void initBinder(WebDataBinder b) {
		super.initBinder(b);
		b.registerCustomEditor(Weapon.class, "weapon", weaponEditor);
		b.addValidators(stockInValidator);
	}

	@Autowired
	private WeaponEditor weaponEditor;
	@Autowired
	private StockInValidator stockInValidator;

}
