package io.jianxun.business.repository;

import java.util.List;

import io.jianxun.business.domain.requisitions.RequestForm;
import io.jianxun.business.domain.requisitions.RequestFormAuditor;
import io.jianxun.common.repository.EntityRepository;

public interface RequestFormRepository extends EntityRepository<RequestForm, Long> {

}
