package io.jianxun.business.domain.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import io.jianxun.business.domain.stock.StockIn;

@Component
public class StockInValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return StockIn.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final StockIn e = (StockIn) target;
		if (e.getDepart() == null)
			errors.rejectValue("depart", "depart.notempty", "所属机构不能为空");

		if (e.getWeapon() == null)
			errors.rejectValue("weapon", "weapon.notempty", "装备不能为空");
		if (e.getCapacity() <= 0)
			errors.rejectValue("capacity", "capacity.illage", "入库数量不能小于1");
		try {
			errors.pushNestedPath("depart");
		} catch (Exception ex) {
			errors.popNestedPath();

		}
	}

}
