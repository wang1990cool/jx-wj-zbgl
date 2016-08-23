package io.jianxun.business.service;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.DepartmentEntity;
import io.jianxun.common.service.EntityService;

@Transactional(readOnly = true)
public class DepartmentableService<T extends DepartmentEntity, ID extends Serializable>
		extends EntityService<T, ID> {

	
}
