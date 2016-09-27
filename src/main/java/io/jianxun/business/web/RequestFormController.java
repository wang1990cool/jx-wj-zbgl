package io.jianxun.business.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.jianxun.business.domain.requisitions.RequestForm;
import io.jianxun.business.enums.RequestFormStatus;
import io.jianxun.business.service.DepartmentService;
import io.jianxun.business.service.DepartmentableService;
import io.jianxun.business.service.RequestFormService;
import io.jianxun.business.service.WeaponService;
import io.jianxun.business.web.dto.ReturnDto;
import io.jianxun.common.service.exception.ServiceException;
import io.jianxun.common.utils.Servlets;

@Controller
@RequestMapping("business/requestform")
public class RequestFormController extends DepartmentableController<RequestForm> {

	@Autowired
	private WeaponService weaponService;

	@Autowired
	private DepartmentService departmentService;

	public RequestFormController(DepartmentableService<RequestForm> entityService) {
		super(entityService);
	}

	@Override
	protected void prepareCreateForm(Model model) {
		super.prepareCreateForm(model);
		model.addAttribute("weapons", weaponService.findAll());
	}

	@Override
	protected void prepareModifyForm(Model model) {
		super.prepareModifyForm(model);
		model.addAttribute("weapons", weaponService.findAll());
	}

	@RequestMapping("/up")
	@ResponseBody
	public ReturnDto up(@RequestParam("ids") Long[] ids) {
		for (Long id : ids) {
			((RequestFormService) entityService).up(id);
		}
		return ReturnDto.ok("操作成功!");
	}

	@RequestMapping(value = "commit/tree", method = RequestMethod.GET)
	public String committree(Model model, @RequestParam(value = "orderField", defaultValue = "id") String orderField,
			@RequestParam(value = "orderDirection", defaultValue = "ASC") String orderDirection) {
		try {
			model.addAttribute("tree", mapper.writeValueAsString(departmentService
					.getDepartTree("business/" + getTemplePrefix() + "/commit/page", getRefrashDiv())));
			otherTreeData(model);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new ServiceException("获取树形数据失败!");
		}
		return getTemplePrefix() + "/tree";

	}

	@RequestMapping("commit/page/{depart}")
	public String commitpage(Model model, Pageable pageable, @PathVariable("depart") Long depart,
			@RequestParam(value = "orderField", defaultValue = "id") String orderField,
			@RequestParam(value = "orderDirection", defaultValue = "DESC") String orderDirection) {
		// 处理分页及排序
		// 由于B-jui 分页组件设定与pageable 有所差别为简单处理 在此直接代码修改pageable对象
		Sort sort = createSort(orderField, orderDirection);
		Map<String, Object> searchParams = getSearchParam();
		searchParams.put("EQ_status", RequestFormStatus.UP.toString());
		Page<RequestForm> page = entityService.findAll(buildPageable(pageable, sort), searchParams);
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

	@RequestMapping(value = "finish/tree", method = RequestMethod.GET)
	public String finishtree(Model model, @RequestParam(value = "orderField", defaultValue = "id") String orderField,
			@RequestParam(value = "orderDirection", defaultValue = "ASC") String orderDirection) {
		try {
			model.addAttribute("tree",
					mapper.writeValueAsString(departmentService.getDepartTree(
							"business/" + getTemplePrefix() + "/finish/page", getRefrashDiv())));
			otherTreeData(model);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new ServiceException("获取树形数据失败!");
		}
		return getTemplePrefix() + "/tree";

	}

	@RequestMapping("finish/page/{depart}")
	public String finishpage(Model model, Pageable pageable, @PathVariable("depart") Long depart,
			@RequestParam(value = "orderField", defaultValue = "id") String orderField,
			@RequestParam(value = "orderDirection", defaultValue = "DESC") String orderDirection) {
		// 处理分页及排序
		// 由于B-jui 分页组件设定与pageable 有所差别为简单处理 在此直接代码修改pageable对象
		Sort sort = createSort(orderField, orderDirection);
		Map<String, Object> searchParams = getSearchParam();
		searchParams.put("EQ_status", RequestFormStatus.COMMIT.toString());
		Page<RequestForm> page = entityService.findAll(buildPageable(pageable, sort), searchParams);
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

	@RequestMapping("/commit")
	@ResponseBody
	public ReturnDto commit(@RequestParam("ids") Long[] ids) {
		for (Long id : ids) {
			((RequestFormService) entityService).commit(id);
		}
		return ReturnDto.ok("操作成功!");
	}

	@RequestMapping(value = "/out/{id}", method = RequestMethod.GET)
	public String modify(@PathVariable("id") Long id, Model model) {
		RequestForm f = entityService.findOne(id);
		model.addAttribute(getDomainName(), f);
		return getTemplePrefix() + "/outform";

	}

}
