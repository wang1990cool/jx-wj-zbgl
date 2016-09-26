package io.jianxun.business.domain.editor;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jianxun.business.domain.Role;
import io.jianxun.business.service.RoleService;

@Component
public class RoleEditor extends PropertyEditorSupport {

	@Autowired
	private RoleService roleService;

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text)) {
			Long id = Long.parseLong(text);
			setValue(roleService.findOne(id));
		}
	}

	/**
	 * Set to page
	 */
	@Override
	public String getAsText() {
		if (getValue() != null)
			return ((Role) getValue()).getName();
		return "";
	}

}
