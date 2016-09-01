package io.jianxun.business.enums;

import java.util.Map;

import com.google.common.collect.Maps;

public enum StockInCategory {
	ROOT("root", "总队购置"), NO_ROOT("noRoot", "支队购置");

	private String code;
	private String name;

	private StockInCategory(String code, String name) {
		this.code = code;
		this.name = name;
	}

	private static Map<String, StockInCategory> valueMap = Maps.newHashMap();

	static {
		for (StockInCategory u : StockInCategory.values()) {
			valueMap.put(u.code, u);
		}
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

	public static String parse(String code) {
		StockInCategory u = valueMap.get(code);
		if (u != null)
			return u.getName();
		return "";
	}

}
