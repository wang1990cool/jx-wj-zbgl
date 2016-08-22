package io.jianxun.common.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.web.dto.BaseTree;
import io.jianxun.common.domain.TreeableEntity;

@Transactional(readOnly = true)
public abstract class TreeableEntityService<T extends TreeableEntity, ID extends Serializable>
		extends EntityService<T, ID> {

	public List<T> findAll(Sort sort) {
		return entityRepository.findAll(sort);
	}

	public List<? extends BaseTree> getTree(Sort sort) {
		// TODO 根据用户信息获取对应的机构信息
		List<T> list = findAll();
		return convertEntityToTree(list);
	}


	protected abstract List<? extends BaseTree> convertEntityToTree(List<T> list);

}
