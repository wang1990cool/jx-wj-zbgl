package io.jianxun.business.domain.editor;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jianxun.business.domain.Department;
import io.jianxun.business.service.DepartmentService;

@Component
public class DepartmentEditor extends PropertyEditorSupport {

	@Autowired
	private DepartmentService departmentService;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text)) {
			Long id = Long.parseLong(text);
			setValue(departmentService.findOne(id));
		}
	}

	/**
	 * Set to page
	 */
	@Override
	public String getAsText() {
		return ((Department) getValue()).getName();
	}

}
