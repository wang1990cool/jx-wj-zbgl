package io.jianxun.business.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import io.jianxun.business.domain.Department;
import io.jianxun.business.web.dto.DepartmentTree;
import io.jianxun.common.service.EntityService;
import io.jianxun.common.web.EntityController;

@Controller
@RequestMapping(value = "/business/department")
public class DepartController extends EntityController<Department, Long> {
	
	ObjectMapper mapper = new ObjectMapper();

	public DepartController(EntityService<Department, Long> entityService) {
		super(entityService);
	}

	@RequestMapping(value = "tree", method = RequestMethod.GET)
	public String tree(Model model) {
		DepartmentTree tree1 = new DepartmentTree();
		tree1.setId(1L);
		tree1.setpId(0L);
		tree1.setName("父节点1");
		DepartmentTree tree2 = new DepartmentTree();
		tree2.setId(2L);
		tree2.setpId(1L);
		tree2.setName("父节点1-子节点");
		DepartmentTree tree3 = new DepartmentTree();
		tree3.setId(3L);
		tree3.setpId(2L);
		tree3.setName("父节点1-子节点-子节点");
		try {
			model.addAttribute("tree", mapper.writeValueAsString(Lists.newArrayList(tree1,tree2,tree3)));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return getTemplePrefix() + "/tree";

	}

}
