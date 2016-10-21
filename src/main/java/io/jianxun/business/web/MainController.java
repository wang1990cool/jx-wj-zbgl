package io.jianxun.business.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Lists;

import io.jianxun.business.domain.DataDictionary;
import io.jianxun.business.service.DataDicService;
import io.jianxun.business.service.DepartmentService;

@Controller
@RequestMapping(value = "main")
public class MainController {

	@Autowired
	private DataDicService dataDicService;
	@Autowired
	private DepartmentService departmentService;

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
