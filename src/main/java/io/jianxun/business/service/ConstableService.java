package io.jianxun.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.Constable;

@Service
@Transactional(readOnly = true)
public class ConstableService extends DepartmentableService<Constable, Long> {

}
