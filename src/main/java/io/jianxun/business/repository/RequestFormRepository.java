package io.jianxun.business.repository;

import java.time.LocalDate;
import java.util.List;

import io.jianxun.business.domain.requisitions.RequestForm;
import io.jianxun.common.repository.EntityRepository;

public interface RequestFormRepository extends EntityRepository<RequestForm, Long> {

	List<RequestForm> findByReturnDateBefore(LocalDate noticeDate);

	List<RequestForm> findByReturnDateAfter(LocalDate noticeDate);

}
