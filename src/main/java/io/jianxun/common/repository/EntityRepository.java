package io.jianxun.common.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import io.jianxun.business.domain.Department;

@NoRepositoryBean
public interface EntityRepository<T, ID extends Serializable>
		extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

	boolean isNew(T entity);

	Class<T> getDomainClazz();

}
