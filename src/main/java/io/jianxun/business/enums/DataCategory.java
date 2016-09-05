package io.jianxun.business.enums;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.jianxun.business.web.dto.SelectOptionDto;

public enum DataCategory {

	WEAPON("zblb", "装备器械类别");

	private String code;
	private String name;

	private DataCategory(String code, String name) {
		this.code = code;
		this.name = name;
	}

	private static Map<String, DataCategory> valueMaps = Maps.newHashMap();

	static {
		for (DataCategory category : DataCategory.values()) {
			valueMaps.put(category.code, category);
		}
	}

	public static DataCategory parse(String code) {
		return valueMaps.get(code);
	}

	public static List<SelectOptionDto> getSelectOptions() {
		List<SelectOptionDto> options = Lists.newArrayList();
		for (DataCategory u : DataCategory.values()) {
			SelectOptionDto e = convert2SelectOption(u);
			options.add(e);
		}
		return options;
	}
	

	private static SelectOptionDto convert2SelectOption(DataCategory u) {
		SelectOptionDto s = new SelectOptionDto();
		s.setCode(u.getCode());
		s.setName(u.getName());
		return s;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
