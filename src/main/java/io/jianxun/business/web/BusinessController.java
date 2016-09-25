package io.jianxun.business.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.jianxun.business.domain.BusinessBaseEntity;
import io.jianxun.business.domain.validator.CodeUniqueValidator;
import io.jianxun.business.service.BusinessBaseEntityService;
import io.jianxun.common.service.EntityService;
import io.jianxun.common.utils.Messages;
import io.jianxun.common.web.EntityController;

public class BusinessController<T extends BusinessBaseEntity> extends EntityController<T> {

	public BusinessController(EntityService<T> entityService) {
		super(entityService);
	}
	
	@Autowired
	private CodeUniqueValidator<T> codeUniqueValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		webDataBinder.addValidators(codeUniqueValidator);
		
	}

	
	@Autowired
	private Messages message;

	// ajax 验证 code 是否重复
	@RequestMapping("check/codeunique")
	@ResponseBody
	public String checkCodeIsUnique(@RequestParam("code") String code, @RequestParam("id") Long id) {
		if (!((BusinessBaseEntityService<T>) this.entityService).validateCodeIsUnique(code, id))
			return message.get("code.unique");
		return "";
	}

}
