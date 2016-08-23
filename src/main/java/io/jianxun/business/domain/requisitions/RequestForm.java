package io.jianxun.business.domain.requisitions;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.jianxun.business.domain.DepartmentEntity;
import io.jianxun.business.domain.Weapon;
import io.jianxun.common.domain.user.UserDetails;

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
	@ManyToOne
	@JoinColumn(name = "weapon_id")
	private Weapon weapon;
	// 申请数量
	private BigDecimal capacity = BigDecimal.ZERO;

	// 申请单创创建时间
	private LocalDate createDate;
	// 申请单创建用户
	@ManyToOne
	@JoinColumn(name = "c_user_id")
	private UserDetails createUser;
	// 领用日期
	private LocalDate requiredDate;
	// 领用人信息
	private String requiredUser;
	// 备注
	private String descrip;

	// 申请单状态 create,back 可修改，up commit 锁定
	@Enumerated(EnumType.ORDINAL)
	private RequestFormStatus status = RequestFormStatus.CREATE;

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

	public UserDetails getCreateUser() {
		return createUser;
	}

	public void setCreateUser(UserDetails createUser) {
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

}
