package io.jianxun.business.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

import io.jianxun.business.domain.BusinessBaseEntity;
import io.jianxun.business.enums.BooleanStatus;
import io.jianxun.business.repository.BusinessBaseRepository;
import io.jianxun.common.service.EntityService;
import io.jianxun.common.service.exception.ServiceException;
import io.jianxun.common.utils.SearchFilter;
import io.jianxun.common.utils.SearchFilter.Operator;

@Transactional(readOnly = true)
public class BusinessBaseEntityService<T extends BusinessBaseEntity> extends EntityService<T> {

	public boolean validateCodeIsUnique(String code, Long id) {
		Long count = 0L;
		if (id != null && id != -1)
			count = ((BusinessBaseRepository<T>) this.entityRepository).countByCodeAndIdNotAndDeleted(code, id,BooleanStatus.False);
		else
			count = ((BusinessBaseRepository<T>) this.entityRepository).countByCodeAndDeleted(code,BooleanStatus.False);
		return count == 0;
	}
	
	public boolean isDeleted(T entity){
		return BooleanStatus.TRUE.equals(entity.getDeleted());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.jianxun.common.service.EntityService#findAll()
	 */
	@Override
	public List<T> findAll() {
		Map<String, SearchFilter> filterParams = Maps.newHashMap();
		filterParams.put(BusinessBaseEntity.DELETED_PROPERTY,
				new SearchFilter(BusinessBaseEntity.DELETED_PROPERTY, Operator.EQ, BooleanStatus.False));
		return this.entityRepository.findAll(buildSpecification(filterParams));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.jianxun.common.service.EntityService#findAll(org.springframework.data.
	 * domain.Pageable)
	 */
	@Override
	public Page<T> findAll(Pageable pageable) {
		return findAll(pageable, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.jianxun.common.service.EntityService#findAll(org.springframework.data.
	 * domain.Pageable, java.util.Map)
	 */
	@Override
	public Page<T> findAll(Pageable pageable, Map<String, Object> searchParams) {
		if (searchParams == null || (!searchParams.containsKey("EQ_" + BusinessBaseEntity.DELETED_PROPERTY)))
			searchParams.put("EQ_" + BusinessBaseEntity.DELETED_PROPERTY, BooleanStatus.False.toString());
		return super.findAll(pageable, searchParams);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.jianxun.common.service.EntityService#save(io.jianxun.common.domain.
	 * IdEntity)
	 */
	@Override
	@Transactional(readOnly = false)
	public T save(T entity) {
		if (BooleanStatus.TRUE.equals(entity.getDeleted()))
			throw new ServiceException("对象已删除,不能进行更新操作!");
		return super.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.jianxun.common.service.EntityService#delete(io.jianxun.common.domain.
	 * IdEntity)
	 */
	@Override
	@Transactional(readOnly = false)
	public void delete(T entity) {
		if (entity.getDeleted() == null || BooleanStatus.False.equals(entity.getDeleted()))
			entity.setDeleted(BooleanStatus.TRUE);
		super.save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.jianxun.common.service.EntityService#deleteInBatch(java.lang.Iterable)
	 */
	@Override
	@Transactional(readOnly = false)
	public void deleteInBatch(Iterable<T> entities) {
		for (T t : entities) {
			delete(t);
		}
	}

}
