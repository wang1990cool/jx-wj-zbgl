package io.jianxun.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.DataDictionary;
import io.jianxun.common.service.EntityService;

@Service
@Transactional(readOnly = true)
public class DataDicService extends EntityService<DataDictionary, Long> {

}
