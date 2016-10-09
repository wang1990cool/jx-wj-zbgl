package io.jianxun.business.domain;

import javax.persistence.MappedSuperclass;

/**
 * 提醒信息实体公共类
 * @author tt
 *
 */
@MappedSuperclass
public class NoticeEntity extends DepartmentEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -63977765396112459L;

	private String message;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
