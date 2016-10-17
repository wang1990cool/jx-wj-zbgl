package io.jianxun.common.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.jianxun.business.web.dto.ReturnDto;
import io.jianxun.common.domain.IdEntity;
import io.jianxun.common.service.EntityService;
import io.jianxun.common.utils.Servlets;

public class EntityController<T extends IdEntity> {

	public static final String AJAX_PREFIX = "/ajax";
	public static final String DOMAINNAME = "11";

	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;

	protected final EntityService<T> entityService;

	public EntityService<T> getEntityService() {
		return entityService;
	}

	@ModelAttribute
	public void getRequestResponse(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	@Autowired
	public EntityController(EntityService<T> entityService) {
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

	@RequestMapping(value = { "", "/page" })
	public String page(Model model, Pageable pageable,
			@RequestParam(value = "orderField", defaultValue = "id") String orderField,
			@RequestParam(value = "orderDirection", defaultValue = "DESC") String orderDirection) {
		// 处理分页及排序
		// 由于B-jui 分页组件设定与pageable 有所差别为简单处理 在此直接代码修改pageable对象
		Sort sort = createSort(orderField, orderDirection);
		Map<String, Object> searchParams = getSearchParam();
		Page<T> page = entityService.findAll(buildPageable(pageable, sort), searchParams);
		model.addAttribute("content", page.getContent());
		model.addAttribute("page", page.getNumber() + 1);
		model.addAttribute("size", page.getSize());
		model.addAttribute("orderField", orderField);
		model.addAttribute("orderDirection", orderDirection);
		model.addAttribute("total", page.getTotalElements());
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		// 提供模板方法 处理非标准数据
		// 如查询条件
		otherPageDate(model);
		return getTemplePrefix() + "/page";
	}

	protected Map<String, Object> getSearchParam() {
		return Servlets.getParametersStartingWith(request, "search_");
	}

	protected Sort createSort(String orderField, String orderDirection) {
		Direction d = Direction.valueOf(orderDirection.toUpperCase());
		if (d == null)
			d = Direction.DESC;
		Sort sort = new Sort(d, orderField);
		return sort;
	}

	protected Pageable buildPageable(Pageable pageable, Sort sort) {
		int page = pageable.getPageNumber() - 1;
		if (page < 0)
			page = 0;
		int pageSize = pageable.getPageSize();
		if (pageSize < 0)
			// 如果没有参数默认显示20条
			pageSize = 20;
		pageable = null;
		return new PageRequest(page, pageSize, sort);

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
		model.addAttribute("action", "create");
		prepareCreateForm(model);
		return getTemplePrefix() + "/form";
	}

	protected void prepareCreateForm(Model model) {

	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDto createSave(@Valid T entity) {
		entityService.save(entity);
		return getSaveReturn();
	}

	protected ReturnDto getSaveReturn() {
		return ReturnDto.tabSuccessReturn("操作成功!", getTemplePrefix() + "-page");
	}

	@RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
	public String modify(@PathVariable("id") Long id, Model model) {
		model.addAttribute(getDomainName(), entityService.findOne(id));
		model.addAttribute("action", "modify");
		prepareModifyForm(model);
		return getTemplePrefix() + "/form";

	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDto modifySave(@Valid @ModelAttribute(name = "domain") T entity, Model model) {
		entityService.save(entity);
		return getSaveReturn();
	}

	protected void prepareModifyForm(Model model) {

	}

	@RequestMapping(value = "/remove/{id}")
	@ResponseBody
	public ReturnDto remove(@PathVariable("id") Long id) {
		entityService.delete(id);
		return ReturnDto.ok("操作成功!");
	}

	@RequestMapping("/remove")
	@ResponseBody
	public ReturnDto batchRemove(@RequestParam("ids") Long[] ids) {
		for (Long id : ids) {
			entityService.delete(id);
		}
		return ReturnDto.ok("操作成功!");
	}

	protected String getDomainName() {
		return entityService.getDomainClassLowName();
	}

	// 获取模板目录 默认以实体名称全小写命名
	protected String getTemplePrefix() {
		return entityService.getDomainClassLowName();
	}

	@ModelAttribute
	public void getMode(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != null&&id!=-1L) {
			T entity = entityService.findOne(id);
			if (entity != null)
				model.addAttribute("domain", entity);
		}
	}

}
