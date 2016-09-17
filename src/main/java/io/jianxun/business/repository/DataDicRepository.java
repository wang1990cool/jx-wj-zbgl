package io.jianxun.business.repository;

import java.util.List;

import io.jianxun.business.domain.DataDictionary;
import io.jianxun.common.repository.EntityRepository;

public interface DataDicRepository extends BusinessBaseRepository<DataDictionary> {

	List<DataDictionary> findByCategory(String category);

}
