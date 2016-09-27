package io.jianxun.business.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.TreeableEntity;
import io.jianxun.business.web.dto.BaseTree;
import io.jianxun.business.web.dto.DepartmentTree;

@Transactional(readOnly = true)
public abstract class TreeableEntityService<T extends TreeableEntity> extends BusinessBaseEntityService<T> {

	public List<? extends BaseTree> getCurrentUserTree() {
		// 获取生成tree的数据源
		List<T> list = getCurrentUserTreeData();
		return convertEntityToTree(list);
	}

	public List<? extends BaseTree> getAllTree() {
		return convertEntityToTree(findAll());
	}

	@SuppressWarnings("unchecked")
	public <S extends DepartmentTree> List<S> getDepartTree(String url, String divid) {
		List<S> list = (List<S>) getCurrentUserTree();
		for (S s : list) {
			s.setUrl(url);
			s.setDivid(divid);
		}
		return list;
	}

	protected abstract List<T> getCurrentUserTreeData();

	protected abstract List<? extends BaseTree> convertEntityToTree(List<T> list);

}
