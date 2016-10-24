package io.jianxun.business.domain.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import io.jianxun.business.domain.User;
import io.jianxun.business.service.UserDetailsService;
import io.jianxun.business.web.dto.PasswordDto;

@Component
public class UserValidator implements Validator {

	@Autowired
	private UserDetailsService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz) || PasswordDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final User e = (User) target;
		if (e.getConstable()==null)
			errors.rejectValue("constable", "constable.notempty", "所属警员不能为空");
		if(!userService.validatConstableIsUnique(e.getConstable(), e.getId()))
			errors.rejectValue("constable", "constable.unique", "所属警员已经使用");
	}

}
