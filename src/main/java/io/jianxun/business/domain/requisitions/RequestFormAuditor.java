package io.jianxun.business.domain.requisitions;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.jianxun.business.domain.AuditEntity;
import io.jianxun.business.enums.RequestFormStatus;

/**
 * 申请审核记录表
 * 
 * @author tongtn
 *
 *         createDate: 2016-09-30
 */
@Entity
@Table(name = "jx_zbgl_rf_audits")
public class RequestFormAuditor extends AuditEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6612157501111659931L;
	private RequestForm requestForm;

	// 申请单状态 create,back 可修改，up commit 锁定
	private RequestFormStatus status = RequestFormStatus.CREATE;

	//审批信息
	private String message;

	@ManyToOne
	@JoinColumn(name = "rf_id")
	public RequestForm getRequestForm() {
		return requestForm;
	}

	public void setRequestForm(RequestForm requestForm) {
		this.requestForm = requestForm;
	}

	@Enumerated(EnumType.ORDINAL)
	public RequestFormStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(RequestFormStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
