package io.jianxun.business.domain.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import io.jianxun.business.domain.Weapon;
import io.jianxun.business.service.WeaponService;

@Component
public class WeaponUniqueValidator implements Validator {

	@Autowired
	private WeaponService weaponService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Weapon.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final Weapon e = (Weapon) target;
		if (StringUtils.isEmpty(e.getName()))
			errors.rejectValue("name", "name.notempty", "装备名称不能为空");
		if(!weaponService.validateNameAndTypeIsUnique(e.getName(), e.getType(), e.getId()))
			errors.rejectValue("name", "name.unique", "装备名称和类型重复");
	}

}
