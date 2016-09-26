package io.jianxun.common.utils;

import org.hibernate.Hibernate;

public class Hibernates {
	public static void initLazyProperty(Object proxyedPropertyValue) {
		Hibernate.initialize(proxyedPropertyValue);
	}

}
