package io.jianxun.business.service;

import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.DepartmentEntity;

@Transactional(readOnly = true)
public class DepartmentableService<T extends DepartmentEntity>
		extends BusinessBaseEntityService<T> {

	
}
