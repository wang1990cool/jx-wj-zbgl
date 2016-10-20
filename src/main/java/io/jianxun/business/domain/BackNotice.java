package io.jianxun.business.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.jianxun.business.domain.requisitions.RequestForm;

/**
 * 归还通知
 * 
 * @author tt
 *
 */
@Entity
@Table(name="jx_zb_backnotices")
public class BackNotice extends NoticeEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9071897593573128275L;

	private RequestForm requestForm;

	/**
	 * @return the requestForm
	 */
	public RequestForm getRequestForm() {
		return requestForm;
	}

	/**
	 * @param requestForm
	 *            the requestForm to set
	 */
	public void setRequestForm(RequestForm requestForm) {
		this.requestForm = requestForm;
	}

}
