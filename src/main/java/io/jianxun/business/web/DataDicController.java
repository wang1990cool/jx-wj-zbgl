package io.jianxun.business.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jianxun.business.domain.DataDictionary;
import io.jianxun.business.enums.DataCategory;
import io.jianxun.common.service.EntityService;
import io.jianxun.common.web.EntityController;

@Controller
@RequestMapping("business/datadic")
public class DataDicController extends EntityController<DataDictionary, Long> {

	public DataDicController(EntityService<DataDictionary, Long> entityService) {
		super(entityService);
	}

	@Override
	protected void prepareCreateForm(Model model) {
		model.addAttribute("categories", DataCategory.getSelectOptions());
	}

	@Override
	protected void prepareModifyForm(Model model) {
		model.addAttribute("categories", DataCategory.getSelectOptions());
	}

}
