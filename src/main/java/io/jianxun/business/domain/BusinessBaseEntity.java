package io.jianxun.business.domain;

import java.time.LocalDateTime;

import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import io.jianxun.business.enums.BooleanStatus;
import io.jianxun.common.domain.IdEntity;
import io.jianxun.common.domain.user.UserDetails;

/**
 * 业务实体基础属性
 * 
 * @author tt
 *
 */
@MappedSuperclass
public abstract class BusinessBaseEntity extends IdEntity {

	public static final String DELETED_PROPERTY = "deleted";

	/**
	 * 
	 */
	private static final long serialVersionUID = 2766871136543273321L;
	// 编码
	// unique
	private String code;
	// auditor
	private UserDetails createUser;
	private LocalDateTime createTime;
	private UserDetails lastModifedBy;
	private LocalDateTime lastModifiedDate;
	// 逻辑删除标记
	private BooleanStatus deleted = BooleanStatus.False;

	/**
	 * @return the code
	 */
	@NotNull(message="code.illegal")
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the createUser
	 */
	@CreatedBy
	@ManyToOne
	@JoinColumn(name = "created_id")
	public UserDetails getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser
	 *            the createUser to set
	 */
	public void setCreateUser(UserDetails createUser) {
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
	public UserDetails getLastModifedBy() {
		return lastModifedBy;
	}

	/**
	 * @param lastModifedBy
	 *            the lastModifedBy to set
	 */
	public void setLastModifedBy(UserDetails lastModifedBy) {
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

	/**
	 * @return the deleted
	 */
	@Enumerated
	public BooleanStatus getDeleted() {
		return deleted;
	}

	/**
	 * @param deleted
	 *            the deleted to set
	 */
	public void setDeleted(BooleanStatus deleted) {
		this.deleted = deleted;
	}

}
