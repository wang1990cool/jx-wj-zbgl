package io.jianxun.business.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.jianxun.business.domain.Department;
import io.jianxun.business.domain.Weapon;
import io.jianxun.business.domain.requisitions.RequestForm;
import io.jianxun.business.domain.requisitions.RequestFormAuditor;
import io.jianxun.business.domain.stock.Stock;
import io.jianxun.business.domain.stock.StockInDetail;
import io.jianxun.business.domain.validator.RequestFormValidator;
import io.jianxun.business.enums.RequestFormStatus;
import io.jianxun.business.service.DepartmentService;
import io.jianxun.business.service.DepartmentableService;
import io.jianxun.business.service.RequestFormAuditorService;
import io.jianxun.business.service.RequestFormService;
import io.jianxun.business.service.StockInDetailService;
import io.jianxun.business.service.StockService;
import io.jianxun.business.service.WeaponService;
import io.jianxun.business.web.dto.AuditorDto;
import io.jianxun.business.web.dto.ReAuditorDto;
import io.jianxun.business.web.dto.ReturnDto;
import io.jianxun.common.service.exception.ServiceException;
import io.jianxun.common.utils.Servlets;

@Controller
@RequestMapping("business/requestform")
public class RequestFormController extends DepartmentableController<RequestForm> {

	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private RequestFormAuditorService requestFormAuditorService;
	@Autowired
	private StockService stockService;

	@Autowired
	private StockInDetailService detailService;

	@Autowired
	private WeaponService weaponService;

	public RequestFormController(DepartmentableService<RequestForm> entityService) {
		super(entityService);
	}

	@Override
	protected void otherPageDate(Model model) {
		model.addAttribute("createable", true);
	}

	@RequestMapping("/ups")
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
		model.addAttribute("commitable", true);
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		return getTemplePrefix() + "/page";
	}

	@RequestMapping(value = "finish/tree", method = RequestMethod.GET)
	public String finishtree(Model model, @RequestParam(value = "orderField", defaultValue = "id") String orderField,
			@RequestParam(value = "orderDirection", defaultValue = "ASC") String orderDirection) {
		try {
			model.addAttribute("tree", mapper.writeValueAsString(departmentService
					.getDepartTree("business/" + getTemplePrefix() + "/finish/page", getRefrashDiv())));
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
		model.addAttribute("enrollment", true);
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
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

	@RequestMapping(value = "/upform/{id}")
	public String upform(@PathVariable("id") Long id, Model model) {
		RequestForm f = entityService.findOne(id);
		if (f == null)
			throw new ServiceException("申请信息不存在");
		Long parentId = f.getDepart().getpId();
		Department parent = departmentService.findOne(parentId);
		if (parent == null)
			throw new ServiceException("获取机构信息失败");
		Stock stock = stockService.findByWeapon(parent, f.getWeapon());
		if (stock == null)
			throw new ServiceException("库存信息不存");
		model.addAttribute("stockDes", stock.getDescription());
		AuditorDto auditorDto = new AuditorDto();
		auditorDto.setDomainId(id);
		model.addAttribute("auditDto", auditorDto);
		return getTemplePrefix() + "/upform";
	}

	@RequestMapping("/up")
	@ResponseBody
	public ReturnDto up(AuditorDto auditMessage) {
		Long id = auditMessage.getDomainId();
		if (id == null)
			throw new ServiceException("获取申请信息失败");
		RequestForm f = entityService.findOne(id);
		if (f == null)
			throw new ServiceException("申请信息不存在");
		((RequestFormService) entityService).up(f, auditMessage.getMessage());
		return ReturnDto.ok("提交成功!");
	}

	@RequestMapping(value = "/auditform/{id}")
	public String auditForm(@PathVariable("id") Long id, Model model) {
		RequestForm f = entityService.findOne(id);
		if (f == null)
			throw new ServiceException("申请信息不存在");
		Long parentId = f.getDepart().getpId();
		Department parent = departmentService.findOne(parentId);
		if (parent == null)
			throw new ServiceException("获取机构信息失败");
		Stock stock = stockService.findByWeapon(parent, f.getWeapon());
		if (stock == null)
			throw new ServiceException("库存信息不存在");
		model.addAttribute("stockDes", stock.getDescription());
		ReAuditorDto auditorDto = new ReAuditorDto();
		auditorDto.setDomainId(id);
		auditorDto.setAuditAmount(f.getCapacity());
		model.addAttribute("auditDto", auditorDto);
		return getTemplePrefix() + "/auditform";
	}

	@RequestMapping("/audit")
	@ResponseBody
	public ReturnDto audit(AuditorDto auditMessage) {
		Long id = auditMessage.getDomainId();
		if (id == null)
			throw new ServiceException("获取申请信息失败");
		RequestForm f = entityService.findOne(id);
		if (f == null)
			throw new ServiceException("申请信息不存在");
		((RequestFormService) entityService).audit(f, auditMessage.getMessage());
		return ReturnDto.ok("审核通过!");
	}

	@RequestMapping("/back")
	@ResponseBody
	public ReturnDto back(AuditorDto auditMessage) {
		Long id = auditMessage.getDomainId();
		if (id == null)
			throw new ServiceException("获取申请信息失败");
		RequestForm f = entityService.findOne(id);
		if (f == null)
			throw new ServiceException("申请信息不存在");
		((RequestFormService) entityService).back(f, auditMessage.getMessage());
		return ReturnDto.ok("打回成功!");
	}

	@RequestMapping(value = "/out/{id}", method = RequestMethod.GET)
	public String outform(@PathVariable("id") Long id, Model model) {
		RequestForm f = entityService.findOne(id);
		model.addAttribute("overview", f.getOverview());
		model.addAttribute("departmentId", f.getDepart().getpId());
		model.addAttribute("weaponId", f.getWeapon().getId());
		return getTemplePrefix() + "/outform";

	}

	@RequestMapping(value = "/out", method = RequestMethod.POST)
	@ResponseBody
	public ReturnDto out(@RequestParam("id") Long id, @RequestParam("detailId") Long[] details, Model model) {
		RequestForm f = entityService.findOne(id);
		if (f == null)
			throw new ServiceException("获取申请信息失败");
		if (details != null && details.length > 0) {
			if (f.getCapacity() < details.length)
				throw new ServiceException("装备选择超出申请数量范围");
			for (Long d : details) {
				StockInDetail detail = detailService.findOne(d);
				if (detail != null)
					f.getDetails().add(detail);
			}
			f.setStatus(RequestFormStatus.ENROLLMENT);
			entityService.save(f);
		} else
			throw new ServiceException("未选择任何装备");
		return ReturnDto.ok("登记成功!");
	}

	// 系统登记
	@RequestMapping(value = "/sysout/{id}")
	@ResponseBody
	public ReturnDto sysout(@PathVariable("id") Long id, Model model) {
		RequestForm f = entityService.findOne(id);
		if (f == null)
			throw new ServiceException("获取申请信息失败");

		Page<StockInDetail> p = detailService.findAll(new PageRequest(0, f.getCapacity(), Direction.DESC, "id"));
		if (p.getContent() != null && !p.getContent().isEmpty()) {
			f.getDetails().addAll(p.getContent());
			f.setStatus(RequestFormStatus.ENROLLMENT);
			entityService.save(f);
		} else
			throw new ServiceException("未选择任何装备");
		return ReturnDto.ok("登记成功!");

	}

	@RequestMapping(value = "/auditmessage/{id}", method = RequestMethod.GET)
	public String auditmessage(@PathVariable("id") Long id, Model model) {
		RequestForm form = ((RequestFormService) this.entityService).findOne(id);
		if (form == null)
			throw new ServiceException("");
		List<RequestFormAuditor> messages = requestFormAuditorService.findByRequestForm(form);
		model.addAttribute("content", messages);
		return getTemplePrefix() + "/auditmessage";
	}

	@RequestMapping(value = "/ajax/search/weapon/detail/{weaponId}/{departId}")
	public String getDepartWeaponDetail(@PathVariable("weaponId") Long weaponId,
			@PathVariable("departId") Long departId, Model model) {

		Department department = departmentService.findOne(departId);
		if (department == null)
			throw new ServiceException("获取机构信息失败");
		Weapon weapon = weaponService.findOne(weaponId);
		if (weapon == null)
			throw new ServiceException("获取装备信息失败");
		model.addAttribute("content", detailService.findByDepartAndStockInWeapon(department, weapon));
		return "requestform/selectweapon";

	}

	@InitBinder
	public void initBinder(WebDataBinder b) {
		super.initBinder(b);
		b.addValidators(requestFormValidator);
	}

	@Autowired
	private RequestFormValidator requestFormValidator;

}
