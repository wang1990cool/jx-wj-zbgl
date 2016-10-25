package io.jianxun.business.event;

import io.jianxun.business.domain.Weapon;

//刷性库存明细相关信息
public class RefreshStockDetailEvent {

	public RefreshStockDetailEvent() {
		super();
	}

	public RefreshStockDetailEvent(Weapon weapon) {
		super();
		this.weapon = weapon;
	}

	private Weapon weapon;

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

}
