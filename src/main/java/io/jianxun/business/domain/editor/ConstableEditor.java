package io.jianxun.business.domain.editor;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jianxun.business.domain.Constable;
import io.jianxun.business.service.ConstableService;

@Component
public class ConstableEditor extends PropertyEditorSupport {

	@Autowired
	private ConstableService service;

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text)) {
			Long id = Long.parseLong(text);
			setValue(service.findOne(id));
		}
	}

	/**
	 * Set to page
	 */
	@Override
	public String getAsText() {
		if (getValue() != null)
			return ((Constable) getValue()).getName();
		return "";
	}

}
