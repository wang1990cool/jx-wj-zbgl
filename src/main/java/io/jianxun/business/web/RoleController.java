package io.jianxun.business.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jianxun.business.domain.Role;
import io.jianxun.business.service.BusinessBaseEntityService;
import io.jianxun.common.domain.user.PermissionDef;

@Controller
@RequestMapping("business/role")
public class RoleController extends BusinessController<Role> {

	public RoleController(BusinessBaseEntityService<Role> entityService) {
		super(entityService);
	}

	@Override
	protected void prepareCreateForm(Model model) {
		model.addAttribute("domains", PermissionDef.DomainDef.getDomainDefs());
		model.addAttribute("perMaps", PermissionDef.getPermission());
	}

	@Override
	protected void prepareModifyForm(Model model) {
		model.addAttribute("domains", PermissionDef.DomainDef.getDomainDefs());
		model.addAttribute("perMaps", PermissionDef.getPermission());
	}

}
