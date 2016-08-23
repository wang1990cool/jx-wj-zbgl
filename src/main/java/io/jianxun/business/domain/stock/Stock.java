package io.jianxun.business.domain.stock;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.jianxun.business.domain.DepartmentEntity;
import io.jianxun.business.domain.Weapon;

@Entity
@Table(name = "wj_zb_stocks")
public class Stock extends DepartmentEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7053189892198693246L;
	// 装备
	@ManyToOne
	@JoinColumn(name = "weapon_id")
	private Weapon weapon;
	// 库存量
	private BigDecimal inventory = BigDecimal.ZERO;

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public BigDecimal getInventory() {
		return inventory;
	}

	public void setInventory(BigDecimal inventory) {
		this.inventory = inventory;
	}

}
