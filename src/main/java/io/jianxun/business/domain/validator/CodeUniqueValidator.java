package io.jianxun.business.domain.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import io.jianxun.business.domain.BusinessBaseEntity;
import io.jianxun.business.service.BusinessBaseEntityService;

@Component
public class CodeUniqueValidator<T extends BusinessBaseEntity> implements Validator {

	@Autowired
	private BusinessBaseEntityService<T> service;

	@Override
	public boolean supports(Class<?> clazz) {
		return BusinessBaseEntity.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final BusinessBaseEntity e = (BusinessBaseEntity) target;
		boolean un = service.validateCodeIsUnique(e.getCode(), e.getId());
		if (!un)
			errors.rejectValue("code","code.unique","编码重复");
	}

}
