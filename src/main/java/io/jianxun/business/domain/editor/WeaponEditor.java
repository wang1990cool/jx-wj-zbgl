package io.jianxun.business.domain.editor;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jianxun.business.domain.Weapon;
import io.jianxun.business.service.WeaponService;

@Component
public class WeaponEditor extends PropertyEditorSupport {

	@Autowired
	private WeaponService weaponService;

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text)) {
			Long id = Long.parseLong(text);
			setValue(weaponService.findOne(id));
		}
	}

	/**
	 * Set to page
	 */
	@Override
	public String getAsText() {
		if (getValue() != null)
			return ((Weapon) getValue()).getName();
		return "";
	}

}
