package io.jianxun.business.domain;

import javax.persistence.MappedSuperclass;

/**
 * 提醒信息实体公共类
 * 
 * @author tt
 *
 */
@MappedSuperclass
public class NoticeEntity extends DepartmentEntity {
	
	public static final Integer OUT_TIME_LEVEL = 99;

	/**
	 * 
	 */
	private static final long serialVersionUID = -63977765396112459L;

	// 0普通到期提醒 99 为过期提醒 数值越大级别越高 列表排序考前
	private int level = 0;

	private String message;

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

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
