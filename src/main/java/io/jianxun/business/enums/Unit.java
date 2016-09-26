package io.jianxun.business.enums;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.jianxun.business.web.dto.CodeNameDto;

public enum Unit {

	YEAR("year", "年"), MONTH("month", "月"), DAY("day", "天");

	private String code;
	private String name;

	private Unit(String code, String name) {
		this.code = code;
		this.name = name;
	}

	private static Map<String, Unit> valueMap = Maps.newHashMap();

	static {
		for (Unit u : Unit.values()) {
			valueMap.put(u.code, u);
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static List<CodeNameDto> getSelectOptions() {
		List<CodeNameDto> options = Lists.newArrayList();
		for (Unit u : Unit.values()) {
			CodeNameDto e = convert2SelectOption(u);
			options.add(e);
		}
		return options;
	}

	private static CodeNameDto convert2SelectOption(Unit u) {
		CodeNameDto s = new CodeNameDto();
		s.setCode(u.getCode());
		s.setName(u.getName());
		return s;
	}

	public static String parse(String code) {
		Unit u = valueMap.get(code);
		if (u != null)
			return u.getName();
		return "";
	}

}
