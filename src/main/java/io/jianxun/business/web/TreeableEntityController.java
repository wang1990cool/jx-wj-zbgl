package io.jianxun.business.web;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jianxun.business.domain.TreeableEntity;
import io.jianxun.business.service.DepartmentService;
import io.jianxun.business.service.TreeableEntityService;
import io.jianxun.business.web.dto.ReturnDto;
import io.jianxun.common.service.exception.ServiceException;
import io.jianxun.common.utils.Servlets;
import io.jianxun.common.web.EntityController;

public class TreeableEntityController<T extends TreeableEntity> extends EntityController<T> {

	ObjectMapper mapper = new ObjectMapper();

	public TreeableEntityController(TreeableEntityService<T> entityService) {
		super(entityService);
	}

	@RequestMapping(value = "tree", method = RequestMethod.GET)
	public String tree(Model model, @RequestParam(value = "orderField", defaultValue = "id") String orderField,
			@RequestParam(value = "orderDirection", defaultValue = "ASC") String orderDirection) {
		try {
			model.addAttribute("tree",
					mapper.writeValueAsString(((TreeableEntityService<T>) entityService).getCurrentUserTree()));
			otherTreeData(model);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new ServiceException("获取树形数据失败!");
		}
		return getTemplePrefix() + "/tree";

	}

	protected void otherTreeData(Model model) {

	}

	@RequestMapping(value = { "/page/{pId}" })
	public String pageWithPid(Model model, Pageable pageable, @PathVariable("pId") Long pId,
			@RequestParam(value = "orderField", defaultValue = "id") String orderField,
			@RequestParam(value = "orderDirection", defaultValue = "DESC") String orderDirection) {
		Sort sort = createSort(orderField, orderDirection);
		Map<String, Object> searchParams = getSearchParam();
		searchParams.put("EQ_pId", Long.toString(pId));
		Page<T> page = entityService.findAll(buildPageable(pageable, sort), searchParams);
		model.addAttribute("content", page.getContent());
		model.addAttribute("page", page.getNumber() + 1);
		model.addAttribute("size", page.getSize());
		model.addAttribute("orderField", orderField);
		model.addAttribute("orderDirection", orderDirection);
		model.addAttribute("total", page.getTotalElements());
		model.addAttribute("pId", pId);
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		// 提供模板方法 处理非标准数据
		// 如查询条件
		otherPageDate(model);
		return getTemplePrefix() + "/page";
	}

	@RequestMapping(value = "/create/{pId}", method = RequestMethod.GET)
	public String create(Model model, @PathVariable("pId") Long pId) {
		model.addAttribute(getDomainName(), entityService.getDomain());
		model.addAttribute("action", "create");
		model.addAttribute("pId", pId);
		T parent = entityService.findOne(pId);
		model.addAttribute("parentInfo", parent);
		prepareCreateForm(model);
		return getTemplePrefix() + "/form";
	}

	@RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
	public String modify(@PathVariable("id") Long id, Model model) {
		model.addAttribute(getDomainName(), entityService.findOne(id));
		model.addAttribute("action", "modify");
		T entity = entityService.findOne(id);
		T parent = entityService.findOne(entity.getpId());
		model.addAttribute("pId", entity.getpId());
		model.addAttribute("parentInfo", parent);
		prepareModifyForm(model);
		return getTemplePrefix() + "/form";

	}

	protected ReturnDto getSaveReturn() {
		return ReturnDto.tabSuccessReturn("操作成功!", getTemplePrefix() + "-page");
	}

}
