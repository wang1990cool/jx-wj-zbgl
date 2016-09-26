package io.jianxun.common.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.jianxun.business.domain.Role;
import io.jianxun.business.domain.editor.RoleEditor;
import io.jianxun.business.service.RoleService;
import io.jianxun.business.web.dto.PasswordDto;
import io.jianxun.business.web.dto.ReturnDto;
import io.jianxun.common.domain.user.User;
import io.jianxun.common.domain.user.UserDetails;
import io.jianxun.common.service.EntityService;
import io.jianxun.common.service.UserDetailsService;
import io.jianxun.common.utils.Messages;

@Controller
@RequestMapping("/business/user")
public class UserController extends EntityController<UserDetails> {

	public UserController(EntityService<UserDetails> entityService) {
		super(entityService);
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(Role.class, "roles", roleEditor);

	}

	@Autowired
	private Messages message;

	// ajax 验证 code 是否重复
	@RequestMapping("check/usernameunique")
	@ResponseBody
	public String checkCodeIsUnique(@RequestParam("username") String username, @RequestParam("id") Long id) {
		if (!((UserDetailsService) this.entityService).validateUsernameIsUnique(username, id))
			return message.get("code.unique");
		return "";
	}

	@Override
	protected String getDomainName() {
		return "user";
	}
	

	@RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
	public String modify(@PathVariable("id") Long id, Model model) {
		model.addAttribute(getDomainName(), entityService.findOne(id));
		model.addAttribute("action", "modify");
		prepareModifyForm(model);
		return getTemplePrefix() + "/modifyform";

	}

	@Override
	protected void prepareCreateForm(Model model) {
		model.addAttribute("roledata", roleService.findAll());
	}

	@Override
	protected void prepareModifyForm(Model model) {
		model.addAttribute("roledata", roleService.findAll());
	}

	@RequestMapping(value = "changepassword", method = RequestMethod.GET)
	public String changePassword(Model model) {
		model.addAttribute("pwd", new PasswordDto());
		return getTemplePrefix() + "/changePassword";
	}

	@RequestMapping(value = "changepassword", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDto entityService(@Valid PasswordDto password) {
		User user = ((UserDetailsService) getEntityService()).getCurrentUser();
		if (user == null)
			throw new UsernameNotFoundException("");
		((UserDetailsService) entityService).changePassword((UserDetails) user, password.getNewPassword());
		return ReturnDto.ok("操作成功!");

	}

	@Override
	protected String getTemplePrefix() {
		return "user";
	}

	@Autowired
	private RoleService roleService;

	@Autowired
	private RoleEditor roleEditor;

}
