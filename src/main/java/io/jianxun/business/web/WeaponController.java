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
import io.jianxun.business.enums.DataCategory;
import io.jianxun.business.enums.Unit;
import io.jianxun.business.service.DataDicService;
import io.jianxun.business.web.dto.SelectOptionDto;
import io.jianxun.common.service.EntityService;
import io.jianxun.common.web.EntityController;

@Controller
@RequestMapping("/business/weapon")
public class WeaponController extends EntityController<Weapon, Long> {

	@Autowired
	private DataDicService dataDicService;
	@Autowired
	private DataDictionaryEditor dataDictionaryEditor;
	
	@InitBinder
	public void initBinder(WebDataBinder b) {
		b.registerCustomEditor(DataDictionary.class, "category", dataDictionaryEditor);
	}

	public WeaponController(EntityService<Weapon, Long> entityService) {
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

	private List<SelectOptionDto> getUnitsData() {
		return Unit.getSelectOptions();
	}

	private void getUnits(Model model) {
		model.addAttribute("units", getUnitsData());
	}

	private void getCategories(Model model) {
		List<DataDictionary> dics = dataDicService.findByCategory(DataCategory.WEAPON.getCode());
		model.addAttribute("categories", dataDicService.getSelectOptions(dics));
	}

	@Override
	protected void otherPageDate(Model model) {
		super.otherPageDate(model);
		// 测试数据
		if (getEntityService().findAll().isEmpty()) {
			for (int i = 0; i < 40; i++) {
				Weapon entity = new Weapon();
				entity.setBarcode("1111" + "-" + i);
				entity.setCode("999" + "-" + i);
				entity.setName("测试装备" + "-" + i);
				getEntityService().save(entity);
			}
		}
	}

}
