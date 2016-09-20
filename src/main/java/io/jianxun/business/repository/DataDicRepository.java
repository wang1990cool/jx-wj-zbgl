package io.jianxun.business.repository;

import java.util.List;

import io.jianxun.business.domain.DataDictionary;

public interface DataDicRepository extends BusinessBaseRepository<DataDictionary> {

	List<DataDictionary> findByCategory(String category);

}
