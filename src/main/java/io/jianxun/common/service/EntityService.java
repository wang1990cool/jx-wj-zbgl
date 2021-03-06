package io.jianxun.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.User;
import io.jianxun.common.domain.IdEntity;
import io.jianxun.common.repository.EntityRepository;
import io.jianxun.common.service.exception.ServiceException;
import io.jianxun.common.utils.DynamicSpecifications;
import io.jianxun.common.utils.SearchFilter;

@Transactional(readOnly = true)
public class EntityService<T extends IdEntity> {

	@Autowired
	protected EntityRepository<T, Long> entityRepository;

	public Class<T> getDomainClass() {
		return entityRepository.getDomainClazz();
	}

	public T getDomain() {
		try {
			return getDomainClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new ServiceException("domain init fail");
		}
	}

	public String getDomainClassLowName() {
		return getDomainClass().getSimpleName().toLowerCase();
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
	public T findOne(Long id) {
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
	public Long delete(Long id) {
		T entity = findOne(id);
		delete(entity);
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

	public Page<T> findAll(Pageable pageable, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		return entityRepository.findAll(buildSpecification(filters), pageable);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	protected Specification<T> buildSpecification(Map<String, SearchFilter> filterParams) {
		Specification<T> spec = DynamicSpecifications.bySearchFilter(filterParams.values(), getDomainClass());
		return spec;
	}

	public List<T> findAll() {
		return entityRepository.findAll();
	}

}
