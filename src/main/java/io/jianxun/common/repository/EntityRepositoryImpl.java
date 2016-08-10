package io.jianxun.common.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;

public class EntityRepositoryImpl<T, ID extends Serializable> extends QueryDslJpaRepository<T, ID>
		implements EntityRepository<T, ID> {

	private final EntityManager entityManager;
	private final Class<T> entityClass;

	public EntityRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
		this.entityClass = entityInformation.getJavaType();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}
	
	

}
