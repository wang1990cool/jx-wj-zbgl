package io.jianxun.business.domain;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.jianxun.common.domain.IdEntity;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditEntity extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 544782711033929086L;

	// auditor
	private User createUser;
	private LocalDateTime createTime;
	private User lastModifedBy;
	private LocalDateTime lastModifiedDate;

	@CreatedBy
	@ManyToOne
	@JoinColumn(name = "created_id")
	public User getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser
	 *            the createUser to set
	 */
	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return the createTime
	 */
	@CreatedDate
	public LocalDateTime getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the lastModifedBy
	 */
	@LastModifiedBy
	@ManyToOne
	@JoinColumn(name = "last_modified_id")
	public User getLastModifedBy() {
		return lastModifedBy;
	}

	/**
	 * @param lastModifedBy
	 *            the lastModifedBy to set
	 */
	public void setLastModifedBy(User lastModifedBy) {
		this.lastModifedBy = lastModifedBy;
	}

	/**
	 * @return the lastModifiedDate
	 */
	@LastModifiedDate
	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * @param lastModifiedDate
	 *            the lastModifiedDate to set
	 */
	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}
