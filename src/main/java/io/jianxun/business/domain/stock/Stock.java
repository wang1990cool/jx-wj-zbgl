package io.jianxun.business.domain.stock;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jianxun.business.domain.DepartmentEntity;
import io.jianxun.business.domain.Weapon;

@Entity
@Table(name = "wj_zb_stocks")
public class Stock extends DepartmentEntity {

	ObjectMapper mapper = new ObjectMapper();

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

	// 最小库存(库存过低提醒使用)
	// -1 时不错提醒
	private Integer minInventory = -1;
	// 最大库存（库存过高提醒使用）
	private Integer maxInventory = -1;
	
	private String minMaxdisplay;

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

	@Transient
	public String getSearchDes() {
		if (this.weapon == null)
			return "";
		SearchDes s = new SearchDes();
		s.setStockDes(this.getDescription());
		s.setWeapon(this.getWeapon().getId());
		s.setWeaponName(this.getWeapon().getName());
		try {
			return mapper.writeValueAsString(s);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	/**
	 * @return the minInventory
	 */
	public Integer getMinInventory() {
		return minInventory;
	}

	/**
	 * @param minInventory
	 *            the minInventory to set
	 */
	public void setMinInventory(Integer minInventory) {
		this.minInventory = minInventory;
	}

	/**
	 * @return the maxInventory
	 */
	public Integer getMaxInventory() {
		return maxInventory;
	}

	/**
	 * @param maxInventory
	 *            the maxInventory to set
	 */
	public void setMaxInventory(Integer maxInventory) {
		this.maxInventory = maxInventory;
	}
	

	/**
	 * @return the minMaxdisplay
	 */
	@Transient
	public String getMinMaxdisplay() {
		StringBuilder sb = new StringBuilder();
		if(this.minInventory>0)
			sb.append("下限:" + this.minInventory);
		if(this.maxInventory>0)
			sb.append("上限:" + this.maxInventory);
		this.minMaxdisplay = sb.toString();
		return this.minMaxdisplay;
	}

	/**
	 * @param minMaxdisplay the minMaxdisplay to set
	 */
	public void setMinMaxdisplay(String minMaxdisplay) {
		this.minMaxdisplay = minMaxdisplay;
	}



	class SearchDes {
		private Long weapon;
		private String weaponName;
		private String stockDes;

		public Long getWeapon() {
			return weapon;
		}

		public void setWeapon(Long weapon) {
			this.weapon = weapon;
		}

		public String getWeaponName() {
			return weaponName;
		}

		public void setWeaponName(String weaponName) {
			this.weaponName = weaponName;
		}

		public String getStockDes() {
			return stockDes;
		}

		public void setStockDes(String stockDes) {
			this.stockDes = stockDes;
		}

	}

}
