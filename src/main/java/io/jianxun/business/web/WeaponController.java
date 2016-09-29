package io.jianxun.business.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jianxun.business.domain.DataDictionary;
import io.jianxun.business.domain.Weapon;
import io.jianxun.business.domain.editor.DataDictionaryEditor;
import io.jianxun.business.domain.validator.WeaponUniqueValidator;
import io.jianxun.business.enums.DataCategory;
import io.jianxun.business.enums.Unit;
import io.jianxun.business.service.BusinessBaseEntityService;
import io.jianxun.business.service.DataDicService;
import io.jianxun.business.web.dto.CodeNameDto;

@Controller
@RequestMapping("/business/weapon")
public class WeaponController extends BusinessController<Weapon> {

	@Autowired
	private DataDicService dataDicService;
	@Autowired
	private DataDictionaryEditor dataDictionaryEditor;
	@Autowired
	private WeaponUniqueValidator weaponUniqueValidator;

	@InitBinder
	@Override
	protected void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(DataDictionary.class, "category", dataDictionaryEditor);
		webDataBinder.addValidators(weaponUniqueValidator);
	}

	public WeaponController(BusinessBaseEntityService<Weapon> entityService) {
		super(entityService);
	}

	@Override
	protected void prepareCreateForm(Model model) {
		getUnits(model);
		getCategories(model);
		super.prepareCreateForm(model);
	}

	@Override
	protected void prepareModifyForm(Model model) {
		getUnits(model);
		getCategories(model);
		super.prepareModifyForm(model);
	}

	private List<CodeNameDto> getUnitsData() {
		return Unit.getSelectOptions();
	}

	private void getUnits(Model model) {
		model.addAttribute("units", getUnitsData());
	}

	private void getCategories(Model model) {
		List<DataDictionary> dics = dataDicService.findByCategory(DataCategory.WEAPON.getCode());
		model.addAttribute("categories", dataDicService.getSelectOptions(dics));
	}

}
