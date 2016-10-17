package io.jianxun.business.domain.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import io.jianxun.business.domain.Department;
import io.jianxun.business.domain.requisitions.RequestForm;
import io.jianxun.business.domain.stock.Stock;
import io.jianxun.business.service.DepartmentService;
import io.jianxun.business.service.StockService;
import io.jianxun.business.web.dto.AuditorDto;

@Component
public class RequestFormValidator implements Validator {

	@Autowired
	private StockService stockService;

	@Autowired
	private DepartmentService departmentService;

	@Override
	public boolean supports(Class<?> clazz) {
		return RequestForm.class.isAssignableFrom(clazz) || AuditorDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		final RequestForm e = (RequestForm) target;
		if (e.getDepart() == null)
			errors.rejectValue("depart", "depart.notempty", "所属机构不能为空");

		if (e.getWeapon() == null)
			errors.rejectValue("weapon", "weapon.notempty", "装备不能为空");
		if (e.getCapacity() <= 0)
			errors.rejectValue("capacity", "capacity.illage", "申请数量小于1");

		Department depart = departmentService.findOne(e.getDepart().getId());
		if (depart.getpId() == null)
			errors.rejectValue("capacity", "capacity.illage", "获取上级机构信息出错");
		Department parent = departmentService.findOne(depart.getpId());
		Stock stock = stockService.findByWeapon(parent, e.getWeapon());
		if (stock == null)
			errors.rejectValue("capacity", "capacity.illage", "库存数量为0");
		if (stock.getInventory() < e.getCapacity())
			errors.rejectValue("capacity", "capacity.illage", "申请数量大于库存数量");

	}

}
