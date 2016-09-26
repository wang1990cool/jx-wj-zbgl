package io.jianxun.business.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jianxun.business.domain.DataDictionary;
import io.jianxun.business.enums.DataCategory;
import io.jianxun.business.service.BusinessBaseEntityService;

@Controller
@RequestMapping("business/datadic")
public class DataDicController extends BusinessController<DataDictionary> {

	public DataDicController(BusinessBaseEntityService<DataDictionary> entityService) {
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
