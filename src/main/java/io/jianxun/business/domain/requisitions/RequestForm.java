package io.jianxun.business.domain.requisitions;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import com.google.common.collect.Sets;

import io.jianxun.business.domain.DepartmentEntity;
import io.jianxun.business.domain.User;
import io.jianxun.business.domain.Weapon;
import io.jianxun.business.domain.stock.StockInDetail;
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
	private Integer capacity = 1;

	// 申请单创创建时间
	private LocalDate createDate;
	// 申请单创建用户
	private User createUser;
	// 领用日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate requiredDate = LocalDate.now();
	// 领用人信息
	private String requiredUser;
	// 备注
	private String descrip;

	// 审核状态 create,back 可修改，up commit 锁定
	private RequestFormStatus status = RequestFormStatus.CREATE;

	// 具体的库存装备信息
	private Set<StockInDetail> details = Sets.newHashSet();

	// 归还日期
	//9999-12-31 表示领用 合理 日期 表示借用
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate returnDate = LocalDate.of(9999, 12, 31);

	@NotNull
	@ManyToOne
	@JoinColumn(name = "weapon_id")
	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	@Min(1)
	@Max(100000)
	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
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
	 * @param status
	 *            the status to set
	 */
	public void setStatus(RequestFormStatus status) {
		this.status = status;
	}

	@ManyToMany
	@JoinTable(name = "jx_zb_ref_details", joinColumns = { @JoinColumn(name = "ref_id") }, inverseJoinColumns = {
			@JoinColumn(name = "detail_id") })
	// Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	// 集合按id排序.
	@OrderBy("id")
	public Set<StockInDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<StockInDetail> details) {
		this.details = details;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	@Transient
	public String getOverview() {
		if (this.getWeapon() == null)
			return "";
		return "申请机构:" + this.getDepart().getSimpleName() + "|申请装备:" + this.getWeapon().getName() + "|申请数量:"
				+ this.getCapacity();

	}

}
