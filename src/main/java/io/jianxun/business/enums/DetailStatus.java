package io.jianxun.business.enums;

import java.util.Map;

import com.google.common.collect.Maps;

public enum DetailStatus {

	ACTIVE("active", "可用"), SELECTED("selected", "已选"), SCRAPPED("scrapped", "报废");

	private DetailStatus(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	private static Map<String, DetailStatus> valueMap = Maps.newHashMap();
	
	static {
		for (DetailStatus u : DetailStatus.values()) {
			valueMap.put(u.code, u);
		}
	}

	private String code;
	private String name;

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
	
	public static String parse(String code) {
		DetailStatus u = valueMap.get(code);
		if (u != null)
			return u.getName();
		return "";
	}

}
