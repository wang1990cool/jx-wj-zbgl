package io.jianxun.business.domain.requisitions;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import io.jianxun.business.domain.DepartmentEntity;
import io.jianxun.business.domain.User;
import io.jianxun.business.domain.Weapon;
import io.jianxun.business.enums.RequestFormStatus;

/**
 * 领用申请单
 * 
 * @author tongtn
 *
 *         createDate: 2016-08-23
 */
@Entity
@Table(name = "wj_zb_rq_forms")
public class RequestForm extends DepartmentEntity {

	private static final long serialVersionUID = 1057179651112200284L;
	private Weapon weapon;
	// 申请数量
	private BigDecimal capacity = BigDecimal.ZERO;

	// 申请单创创建时间
	private LocalDate createDate;
	// 申请单创建用户
	private User createUser;
	// 领用日期
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate requiredDate = LocalDate.now();
	// 领用人信息
	private String requiredUser;
	// 备注
	private String descrip;

	// 审核状态 create,back 可修改，up commit 锁定
	private RequestFormStatus status = RequestFormStatus.CREATE;

	@ManyToOne
	@JoinColumn(name = "weapon_id")
	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public BigDecimal getCapacity() {
		return capacity;
	}

	public void setCapacity(BigDecimal capacity) {
		this.capacity = capacity;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	@ManyToOne
	@JoinColumn(name = "c_user_id")
	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public LocalDate getRequiredDate() {
		return requiredDate;
	}

	public void setRequiredDate(LocalDate requiredDate) {
		this.requiredDate = requiredDate;
	}

	public String getRequiredUser() {
		return requiredUser;
	}

	public void setRequiredUser(String requiredUser) {
		this.requiredUser = requiredUser;
	}

	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

	/**
	 * @return the status
	 */
	@Enumerated(EnumType.ORDINAL)
	public RequestFormStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(RequestFormStatus status) {
		this.status = status;
	}
	
	

}
