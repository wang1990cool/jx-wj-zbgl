package io.jianxun.business.domain.stock;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	// 当前机构库存量
	private Integer inventory = 0;
	// 机构及以下总量
	private Long total;

	// 库存情况
	// 反应该种备件在库存中的分布和装备情况
	private String description;

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

	@Transient
	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	@Transient
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
