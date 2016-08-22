package io.jianxun.common.utils;

import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jianxun.business.service.DepartmentService;
import io.jianxun.common.service.exception.ServiceException;

@Component("departLevelCodeSerialNumber")
public class DepartLevelCodeSerialNumber extends AbstractSerialNumber implements SerialNumber {

	@Override
	public String getSerialNumber(String prefix) throws ServiceException {
		return getSerialNumberEntityNumber(prefix);
	}

	@Override
	public int getMaxSerialNumber(String prefix) throws ServiceException {
		String currentLevelNum = departService.getMaxLevelCode(prefix, prefix.length() + getNumWidth());
		if (StringUtils.isBlank(currentLevelNum))
			return 0;
		try {
			return format(getNumWidth(), getChar())
					.parse(StringUtils.substring(currentLevelNum, currentLevelNum.length() - getNumWidth())).intValue();
		} catch (ParseException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	@Autowired
	private DepartmentService departService;

}
