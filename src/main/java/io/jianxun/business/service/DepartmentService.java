package io.jianxun.business.service;

import org.springframework.stereotype.Service;

import io.jianxun.business.domain.Department;
import io.jianxun.business.repository.DepartRepository;
import io.jianxun.common.service.EntityService;

@Service
public class DepartmentService extends EntityService<Department, Long> {

	public String getMaxLevelCode(String parenetLevelCode, int levelCodeLength) {
		String max = ((DepartRepository) entityRepository).findMaxLevelCode(parenetLevelCode + "%", levelCodeLength);
		return max;
	}

}
