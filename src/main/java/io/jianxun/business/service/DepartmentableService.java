package io.jianxun.business.service;

import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.DepartmentEntity;
import io.jianxun.common.service.EntityService;

@Transactional(readOnly = true)
public class DepartmentableService<T extends DepartmentEntity>
		extends EntityService<T> {

	
}
