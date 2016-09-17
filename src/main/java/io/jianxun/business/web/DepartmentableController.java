package io.jianxun.business.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jianxun.business.domain.Department;
import io.jianxun.business.domain.DepartmentEntity;
import io.jianxun.business.domain.editor.DepartmentEditor;
import io.jianxun.business.service.DepartmentService;
import io.jianxun.business.service.DepartmentableService;
import io.jianxun.common.service.exception.ServiceException;
import io.jianxun.common.utils.Servlets;
import io.jianxun.common.web.EntityController;

public class DepartmentableController<T extends DepartmentEntity>
		extends EntityController<T> {

	ObjectMapper mapper = new ObjectMapper();

	public DepartmentableController(DepartmentableService<T> entityService) {
		super(entityService);
	}

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(value = "tree", method = RequestMethod.GET)
	public String tree(Model model, @RequestParam(value = "orderField", defaultValue = "id") String orderField,
			@RequestParam(value = "orderDirection", defaultValue = "ASC") String orderDirection) {
		try {
			model.addAttribute("tree", mapper.writeValueAsString(departmentService
					.getDepartTree(createSort(orderField, orderDirection), getUrl(), getRefrashDiv())));
			otherTreeData(model);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new ServiceException("获取树形数据失败!");
		}
		return getTemplePrefix() + "/tree";

	}

	protected String getUrl() {
		return "business/" + getTemplePrefix() + "/page";
	}

	protected String getRefrashDiv() {
		return "#" + getTemplePrefix() + "-page-layout";
	}

	protected void otherTreeData(Model model) {

	}

	@RequestMapping(value = { "/page/{depart}" })
	public String pageWithPid(Model model, Pageable pageable, @PathVariable("depart") Long depart,
			@RequestParam(value = "orderField", defaultValue = "id") String orderField,
			@RequestParam(value = "orderDirection", defaultValue = "DESC") String orderDirection) {
		Department parent = departmentService.findOne(depart);
		if(parent==null)
			throw new ServiceException("获取机构仓库信息失败！");
		Sort sort = createSort(orderField, orderDirection);
		Map<String, Object> searchParams = getSearchParam();
		searchParams.put("LIKE_depart.levelCode", parent.getLevelCode()+"%");
		Page<T> page = entityService.findAll(buildPageable(pageable, sort), searchParams);
		model.addAttribute("content", page.getContent());
		model.addAttribute("page", page.getNumber() + 1);
		model.addAttribute("size", page.getSize());
		model.addAttribute("orderField", orderField);
		model.addAttribute("orderDirection", orderDirection);
		model.addAttribute("total", page.getTotalElements());
		model.addAttribute("dId", depart);
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		// 提供模板方法 处理非标准数据
		// 如查询条件
		otherPageDate(model);
		return getTemplePrefix() + "/page";
	}

	@RequestMapping(value = "/create/{d_id}", method = RequestMethod.GET)
	public String create(Model model, @PathVariable("d_id") Long dId) {
		model.addAttribute(getDomainName(), entityService.getDomain());
		model.addAttribute("action", "create");
		Department parent = departmentService.findOne((Long) dId);
		model.addAttribute("department", parent);
		prepareCreateForm(model);
		return getTemplePrefix() + "/form";
	}
	
	@RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
	public String modify(@PathVariable("id") Long id, Model model) {
		model.addAttribute(getDomainName(), entityService.findOne(id));
		model.addAttribute("action", "modify");
		T entity = entityService.findOne(id);
		model.addAttribute("department", entity.getDepart());
		prepareModifyForm(model);
		return getTemplePrefix() + "/form";

	}

	@InitBinder
	public void initBinder(WebDataBinder b) {
		b.registerCustomEditor(Department.class, "depart", departEditor);
	}

	@Autowired
	private DepartmentEditor departEditor;

}
