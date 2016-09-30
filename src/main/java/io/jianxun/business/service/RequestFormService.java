package io.jianxun.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.requisitions.RequestForm;
import io.jianxun.business.enums.RequestFormStatus;

@Service
@Transactional(readOnly = true)
public class RequestFormService extends DepartmentableService<RequestForm> {

	@Autowired
	private RequestFormAuditorService requestFormAuditorService;

	@Transactional(readOnly = false)
	public void up(Long id) {
		RequestForm r = findOne(id);
		if (r != null) {
			r.setStatus(RequestFormStatus.UP);
			requestFormAuditorService.audit(r, "用户提交");
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
