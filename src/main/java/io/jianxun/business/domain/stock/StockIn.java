package io.jianxun.business.domain.stock;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import io.jianxun.business.domain.DepartmentEntity;
import io.jianxun.business.domain.Weapon;
import io.jianxun.business.enums.StockInCategory;

/**
 * 入库单
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
	private Integer capacity = 1;

	// 描述
	private String descrip;

	// 生产日期 用于计算保养和维护提醒时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate productionDate;

	private String stockInCategory = StockInCategory.ROOT.getCode();

	// 记录部分装备自带编号
	private String weaponCodes;

	@ManyToOne
	@JoinColumn(name = "weapon_id")
	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity
	 *            the capacity to set
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}



	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

	@Past
	public LocalDate getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(LocalDate productionDate) {
		this.productionDate = productionDate;
	}

	/**
	 * @return the stockInCategory
	 */
	public String getStockInCategory() {
		return stockInCategory;
	}

	/**
	 * @param stockInCategory
	 *            the stockInCategory to set
	 */
	public void setStockInCategory(String stockInCategory) {
		this.stockInCategory = stockInCategory;
	}

	@Transient
	public String getStockInCategoryName() {
		return StockInCategory.parse(this.getStockInCategory());
	}

	/**
	 * @return the weaponCodes
	 */
	public String getWeaponCodes() {
		return weaponCodes;
	}

	/**
	 * @param weaponCodes
	 *            the weaponCodes to set
	 */
	public void setWeaponCodes(String weaponCodes) {
		this.weaponCodes = weaponCodes;
	}

}
