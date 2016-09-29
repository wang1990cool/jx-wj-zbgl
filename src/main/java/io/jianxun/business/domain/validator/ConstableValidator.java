package io.jianxun.business.domain.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import io.jianxun.business.domain.Constable;

@Component
public class ConstableValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Constable.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final Constable e = (Constable) target;
		if (e.getDepart() == null)
			errors.rejectValue("depart", "depart.notempty", "所属机构不能为空");

	}

}
