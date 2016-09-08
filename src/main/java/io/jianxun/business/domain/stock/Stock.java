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
	private Weapon weapon;
	// 库存量
	private Integer inventory = 0;

	@ManyToOne
	@JoinColumn(name = "weapon_id")
	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	/**
	 * @return the inventory
	 */
	public Integer getInventory() {
		return inventory;
	}

	/**
	 * @param inventory
	 *            the inventory to set
	 */
	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

}
