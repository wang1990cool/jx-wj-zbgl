package io.jianxun.business.domain.validator;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import io.jianxun.business.domain.BusinessBaseEntity;
import io.jianxun.business.service.BusinessBaseEntityService;

public class CodeUniqueValidator<T extends BusinessBaseEntity> implements Validator {

	public CodeUniqueValidator(BusinessBaseEntityService<T> service) {
		super();
		this.service = service;
	}

	private BusinessBaseEntityService<T> service;

	@Override
	public boolean supports(Class<?> clazz) {
		return BusinessBaseEntity.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final BusinessBaseEntity e = (BusinessBaseEntity) target;
		if (StringUtils.isEmpty(e.getCode()))
			errors.rejectValue("code", "code.notempty", "编码不能为空");
		boolean un = service.validateCodeIsUnique(e.getCode(), e.getId());
		if (!un)
			errors.rejectValue("code", "code.unique", "编码重复");
	}

}
