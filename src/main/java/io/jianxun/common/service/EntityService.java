package io.jianxun.common.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.common.domain.IdEntity;
import io.jianxun.common.domain.user.User;
import io.jianxun.common.repository.EntityRepository;
import io.jianxun.common.repository.EntityRepositoryImpl;

@Transactional(readOnly = true)
public class EntityService<T extends IdEntity, ID extends Serializable> {

	@Autowired
	protected EntityRepository<T, ID> entityRepository;

	public String getDomainClassLowName() {
		return ((EntityRepositoryImpl<T, ID>) entityRepository).getEntityClass().getSimpleName().toLowerCase();
	}

	/*
	 * 获取当前登陆用户名称信息
	 */
	public User getCurrentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}

		return ((User) authentication.getPrincipal());
	}

	/**
	 * 读取单个对象
	 * 
	 * @param id
	 * @return
	 */
	public T findOne(ID id) {
		return entityRepository.findOne(id);
	}

	/**
	 * 保存单个对象
	 * 
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false)
	public T save(T entity) {
		return entityRepository.save(entity);
	}

	/**
	 * 批量保存对象
	 * 
	 * @param entities
	 * @return
	 */
	@Transactional(readOnly = false)
	public <S extends T> List<S> save(Iterable<S> entities) {
		return entityRepository.save(entities);
	}

	/**
	 * 刷新修改
	 */
	public void flush() {
		entityRepository.flush();
	}

	@Transactional(readOnly = false)
	public ID delete(ID id) {
		entityRepository.delete(id);
		return id;
	}

	@Transactional(readOnly = false)
	public void delete(T entity) {
		entityRepository.delete(entity);
	}

	@Transactional(readOnly = false)
	public void deleteInBatch(Iterable<T> entities) {
		entityRepository.deleteInBatch(entities);
	}

	public Page<T> findAll(Pageable pageable) {
		return entityRepository.findAll(pageable);
	}

	public List<T> findAll() {
		return entityRepository.findAll();
	}

}
