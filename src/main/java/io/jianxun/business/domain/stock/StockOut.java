package io.jianxun.business.domain.stock;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.jianxun.business.domain.Department;
import io.jianxun.business.domain.Weapon;
import io.jianxun.common.domain.IdEntity;

/**
 * 总库直接出库信息
 * 
 * @author tongtn
 *
 *         createDate: 2016-08-23
 */
@Entity
@Table(name = "")
public class StockOut extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3351408479622117159L;
	// 对应装备
	private Weapon weapon;
	// 领用数量
	private BigDecimal capacity = BigDecimal.ZERO;

	// 领用的下级部门
	private Department to;

	
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

	@ManyToOne
	@JoinColumn(name = "to_id")
	public Department getTo() {
		return to;
	}

	public void setTo(Department to) {
		this.to = to;
	}

}
