package io.jianxun.common.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class EntityRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
		implements EntityRepository<T, ID> {

	private final EntityManager entityManager;
	private final JpaEntityInformation<T, ID> entityInformation;

	public EntityRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
		this.entityInformation = entityInformation;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Class<T> getEntityClass() {
		return entityInformation.getJavaType();
	}

	public boolean isNew(T entity) {
		return entityInformation.isNew(entity);
	}

}
