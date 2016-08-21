package io.jianxun.common.utils;

import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jianxun.business.service.DepartmentService;

@Component("departLevelCodeSerialNumber")
public class DepartLevelCodeSerialNumber extends AbstractSerialNumber implements SerialNumber {

	@Override
	public String getSerialNumber(String prefix) throws Exception {
		return getSerialNumberEntityNumber(prefix);
	}

	@Override
	public int getMaxSerialNumber(String prefix) throws ParseException {
		String currentLevelNum = departService.getMaxLevelCode(prefix, prefix.length() + getNumWidth());
		if (StringUtils.isBlank(currentLevelNum))
			return 0;
		return format(getNumWidth(), getChar())
				.parse(StringUtils.substring(currentLevelNum, currentLevelNum.length() - getNumWidth())).intValue();
	}

	@Autowired
	private DepartmentService departService;

}
