package io.jianxun.business.domain.stock;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.jianxun.business.domain.DepartmentEntity;
import io.jianxun.business.domain.Weapon;
import io.jianxun.common.domain.user.UserDetails;

/**
 * 总库入库明细
 * 
 * @author tongtn
 *
 *         createDate: 2016-08-23
 */
@Entity
@Table(name = "wj_zb_stock_ins")
public class StockIn extends DepartmentEntity {

	private static final long serialVersionUID = -7860940500788914635L;
	// 对应装备
	private Weapon weapon;
	// 入库数量
	private BigDecimal capacity = BigDecimal.ZERO;
	// 入库时间
	private LocalDate createDate;
	// 入库用户
	private UserDetails createUser;

	// 描述
	private String descrip;

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
	@JoinColumn(name = "user_id")
	public UserDetails getCreateUser() {
		return createUser;
	}

	public void setCreateUser(UserDetails createUser) {
		this.createUser = createUser;
	}

	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

}
