package io.jianxun.common.web;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.jianxun.business.web.dto.ReturnDto;
import io.jianxun.common.domain.IdEntity;
import io.jianxun.common.service.EntityService;

public class EntityController<T extends IdEntity, ID extends Serializable> {

	protected HttpServletRequest request;
	protected HttpServletResponse response;

	protected final EntityService<T, ID> entityService;

	public EntityService<T, ID> getEntityService() {
		return entityService;
	}

	@ModelAttribute
	public void getRequestResponse(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	@Autowired
	public EntityController(EntityService<T, ID> entityService) {
		Assert.notNull(entityService, "Service must not be null!");
		this.entityService = entityService;
	}

	/**
	 * page
	 * 
	 * @param model
	 * @param pageable
	 * @param request
	 * @return
	 */

	@RequestMapping(value = { "", "/page" }, method = RequestMethod.GET)
	public String page(Model model, Pageable pageable) {
		Page<T> page = entityService.findAll(pageable);
		model.addAttribute("content", page.getContent());
		model.addAttribute("page", page.getNumber());
		model.addAttribute("size", page.getSize());
		model.addAttribute("total", page.getNumberOfElements());
		// 提供模板方法 处理非标准数据
		otherPageDate(model);
		return getTemplePrefix() + "/page";
	}

	protected void otherPageDate(Model model) {
	}

	/**
	 * list
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, HttpRequest request) {
		List<T> list = entityService.findAll();
		model.addAttribute("content", list);
		otherListDate(model);
		return getTemplePrefix() + "/list";
	}

	protected void otherListDate(Model model) {
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute(getDomainName(), entityService.getDomain());
		prepareCreateForm(model);
		return getTemplePrefix() + "/form";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDto createSave(@Valid T entity) {
		entityService.save(entity);
		return ReturnDto.tabSuccessReturn("操作成功!", getTemplePrefix() + "-page");
	}

	protected String getDomainName() {
		return entityService.getDomainClassLowName();
	}

	protected void prepareCreateForm(Model model) {

	}

	// 获取模板目录 默认以实体名称全小写命名
	protected String getTemplePrefix() {
		return entityService.getDomainClassLowName();
	}

}
