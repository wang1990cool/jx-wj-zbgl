package io.jianxun.business.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Lists;

import io.jianxun.business.domain.DataDictionary;
import io.jianxun.business.domain.Role;
import io.jianxun.business.service.DataDicService;
import io.jianxun.business.service.DepartmentService;
import io.jianxun.business.service.RoleService;
import io.jianxun.common.domain.user.PermissionDef;

@Controller
@RequestMapping(value = "main")
public class MainController {

	@Autowired
	private DataDicService dataDicService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private RoleService roleService;

	@RequestMapping(method = RequestMethod.GET)
	public String main() {
		initData();
		return "main";
	}

	private void initData() {
		if (dataDicService.findAll().size() == 0)
			initDataDic();
		if (departmentService.findAll().size() == 0)
			initDepart();
		if (roleService.findAll().size() <= 1)
			initRole();
	}

	private void initRole() {
		Role sys = new Role();
		sys.setCode("002");
		sys.setName("系统管理角色");
		sys.setPermissions(PermissionDef.getPerGroup("sys"));
		Role weapon = new Role();
		weapon.setCode("003");
		weapon.setName("装备基本信息维护角色");
		weapon.setPermissions(PermissionDef.getPerGroup("weapon"));
		Role stock = new Role();
		stock.setCode("004");
		stock.setName("库存管理角色");
		stock.setPermissions(PermissionDef.getPerGroup("stock"));
		Role sq = new Role();
		sq.setCode("005");
		sq.setName("装备申请/借用角色");
		sq.setPermissions(PermissionDef.getPerGroup("sq"));
		Role sh = new Role();
		sh.setCode("006");
		sh.setName("装备审核角色");
		sh.setPermissions(PermissionDef.getPerGroup("sh"));
		Role dj = new Role();
		dj.setCode("007");
		dj.setName("装备登记角色");
		dj.setPermissions(PermissionDef.getPerGroup("dj"));
		Role ly = new Role();
		ly.setCode("008");
		ly.setName("装备领用确认角色");
		ly.setPermissions(PermissionDef.getPerGroup("ly"));
		roleService.save(Lists.newArrayList(sys, weapon, stock, sq, sh, dj, ly));
	}

	private void initDepart() {
		departmentService.createTestData();
	}

	private void initDataDic() {
		DataDictionary d = new DataDictionary();
		d.setCode("djzb");
		d.setName("单警装备");
		DataDictionary c = new DataDictionary();
		c.setCode("cl");
		c.setName("车辆");
		dataDicService.save(Lists.newArrayList(d, c));
	}

}
