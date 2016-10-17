package io.jianxun.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.requisitions.RequestForm;
import io.jianxun.business.domain.requisitions.RequestFormAuditor;
import io.jianxun.business.repository.RequestFormAuditorRepository;
import io.jianxun.common.service.EntityService;

@Service
@Transactional(readOnly = true)
public class RequestFormAuditorService extends EntityService<RequestFormAuditor> {

	@Autowired
	private RequestFormService requestFormService;

	@Transactional(readOnly = false)
	public void audit(RequestForm requestForm, String message) {
		RequestFormAuditor auditor = new RequestFormAuditor();
		auditor.setStatus(requestForm.getStatus());
		auditor.setRequestForm(requestForm);
		auditor.setMessage(message);
		save(auditor);
		requestFormService.save(requestForm);
	}

	public List<RequestFormAuditor> findByRequestForm(RequestForm form) {
		return ((RequestFormAuditorRepository) this.entityRepository).findByRequestForm(form);
	}

}
