package io.jianxun.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.requisitions.RequestForm;

@Service
@Transactional(readOnly = true)
public class RequestFormService extends DepartmentableService<RequestForm, Long> {

}
