package io.jianxun.common.web;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.jianxun.common.domain.IdEntity;
import io.jianxun.common.service.EntityService;

public class EntityController<T extends IdEntity, ID extends Serializable> {

	private final EntityService<T, ID> entityService;

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
	public String page(Model model, Pageable pageable, HttpRequest request) {
		Page<T> page = entityService.findAll(pageable);
		model.addAttribute("content", page.getContent());
		model.addAttribute("pageNum", pageable.getPageNumber());
		model.addAttribute("pageSize", pageable.getPageSize());
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

	// 获取模板目录 默认以实体名称全小写命名
	protected String getTemplePrefix() {
		return entityService.getDomainClassLowName();
	}

}
