package io.jianxun.business.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.jianxun.business.domain.Constable;
import io.jianxun.business.domain.Role;
import io.jianxun.business.domain.User;
import io.jianxun.business.domain.editor.ConstableEditor;
import io.jianxun.business.domain.editor.RoleEditor;
import io.jianxun.business.domain.validator.UserValidator;
import io.jianxun.business.service.ConstableService;
import io.jianxun.business.service.RoleService;
import io.jianxun.business.service.UserDetailsService;
import io.jianxun.business.web.dto.PasswordDto;
import io.jianxun.business.web.dto.ReturnDto;
import io.jianxun.common.service.EntityService;
import io.jianxun.common.utils.Messages;
import io.jianxun.common.web.EntityController;

@Controller
@RequestMapping("/business/user")
public class UserController extends EntityController<User> {

	public UserController(EntityService<User> entityService) {
		super(entityService);
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(Constable.class, "constable", constableEditor);
		webDataBinder.registerCustomEditor(Role.class, "roles", roleEditor);
		webDataBinder.addValidators(userValidator);

	}

	@Autowired
	private Messages message;

	// ajax 验证 code 是否重复
	@RequestMapping(AJAX_PREFIX + "/check/usernameunique")
	@ResponseBody
	public String checkCodeIsUnique(@RequestParam("username") String username, @RequestParam("id") Long id) {
		if (!((UserDetailsService) this.entityService).validateUsernameIsUnique(username, id))
			return message.get("code.unique");
		return "";
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
		model.addAttribute("constables", constableService.findAll());
		model.addAttribute("roledata", roleService.findAll());
	}

	@Override
	protected void prepareModifyForm(Model model) {
		model.addAttribute("constables", constableService.findAll());
		model.addAttribute("roledata", roleService.findAll());
	}

	@RequestMapping(value = "resetpassword/{id}", method = RequestMethod.GET)
	public String resetPassword(@PathVariable("id") Long id, Model model) {
		model.addAttribute("pwd", new PasswordDto());
		model.addAttribute("id", id);
		return getTemplePrefix() + "/resetpassword";
	}

	@RequestMapping(value = "resetpassword", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDto resetpassword(@RequestParam("id") Long id, PasswordDto password) {
		User user = ((UserDetailsService) getEntityService()).findOne(id);
		if (user == null)
			throw new UsernameNotFoundException("");
		((UserDetailsService) entityService).changePassword((User) user, password.getNewPassword());
		return ReturnDto.ok("操作成功!");

	}

	@RequestMapping(value = "changepassword", method = RequestMethod.GET)
	public String changePassword(Model model) {
		model.addAttribute("pwd", new PasswordDto());
		return getTemplePrefix() + "/changepassword";
	}

	@RequestMapping(value = "changepassword", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDto changepassword(@Valid @ModelAttribute("passworddto") PasswordDto password) {
		User user = ((UserDetailsService) getEntityService()).getCurrentUser();
		if (user == null)
			throw new UsernameNotFoundException("用户不存在");
		((UserDetailsService) entityService).changePassword((User) user, password.getNewPassword());
		return ReturnDto.ok("操作成功!");

	}

	@RequestMapping("locked")
	@ResponseBody
	public ReturnDto batchRemove(@RequestParam("ids") Long[] ids) {
		for (Long id : ids) {
			User user = entityService.findOne(id);
			if (user == null)
				throw new UsernameNotFoundException("用户不存在");
			((UserDetailsService) entityService).accountLocked(user, true);
		}
		return ReturnDto.ok("操作成功!");
	}

	@Autowired
	private RoleService roleService;

	@Autowired
	private ConstableService constableService;

	@Autowired
	private RoleEditor roleEditor;

	@Autowired
	private ConstableEditor constableEditor;

	@Autowired
	private UserValidator userValidator;

}
