package io.jianxun.business.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.jianxun.business.domain.Department;
import io.jianxun.common.repository.EntityRepository;

public interface DepartRepository extends EntityRepository<Department, Long> {
	// 获取相应层级下最大的levelCode

	@Query("select max(d.levelCode) from Department as d where d.levelCode like :levelCode  and length(d.levelCode)=:len")
	String findTopByLevelCodeLikeOrderByLevelCodeDesc(@Param("levelCode") String levelCode, @Param("len") Integer len);
}
