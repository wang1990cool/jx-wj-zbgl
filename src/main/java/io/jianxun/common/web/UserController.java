package io.jianxun.common.web;

import javax.validation.Valid;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.jianxun.business.web.dto.PasswordDto;
import io.jianxun.business.web.dto.ReturnDto;
import io.jianxun.common.domain.user.User;
import io.jianxun.common.domain.user.UserDetails;
import io.jianxun.common.service.EntityService;
import io.jianxun.common.service.UserDetailsService;

@Controller
@RequestMapping("/business/user")
public class UserController extends EntityController<UserDetails, Long> {

	public UserController(EntityService<UserDetails, Long> entityService) {
		super(entityService);
	}

	@RequestMapping(value = "changepassword", method = RequestMethod.GET)
	public String changePassword(Model model) {
		model.addAttribute("pwd", new PasswordDto());
		return "user/changePassword";
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

}
