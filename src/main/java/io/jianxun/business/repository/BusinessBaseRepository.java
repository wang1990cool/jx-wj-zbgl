package io.jianxun.business.repository;

import org.springframework.data.repository.NoRepositoryBean;

import io.jianxun.business.domain.BusinessBaseEntity;
import io.jianxun.business.enums.BooleanStatus;
import io.jianxun.common.repository.EntityRepository;

@NoRepositoryBean
public interface BusinessBaseRepository<T extends BusinessBaseEntity> extends EntityRepository<T, Long> {

	Long countByCodeAndDeleted(String code, BooleanStatus deleted);

	Long countByCodeAndIdNotAndDeleted(String code, Long id, BooleanStatus deleted);
}
