package io.jianxun.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.requisitions.RequestForm;
import io.jianxun.business.domain.requisitions.RequestFormStatus;

@Service
@Transactional(readOnly = true)
public class RequestFormService extends DepartmentableService<RequestForm, Long> {
	@Transactional(readOnly = false)
	public void up(Long id) {
		RequestForm r = findOne(id);
		if (r != null) {
			r.setStatus(RequestFormStatus.UP);
			save(r);
			
		}
	}

	@Transactional(readOnly = false)
	public void commit(Long id) {
		RequestForm r = findOne(id);
		if (r != null) {
			r.setStatus(RequestFormStatus.COMMIT);
			save(r);
		}
	}
	
	@Transactional(readOnly = false)
	public void back(Long id) {
		RequestForm r = findOne(id);
		if (r != null) {
			r.setStatus(RequestFormStatus.BACK);
			save(r);
		}
	}

}
