package io.jianxun.business.event;

import java.util.Set;

import com.google.common.collect.Sets;

import io.jianxun.business.domain.Department;
import io.jianxun.business.domain.Weapon;
import io.jianxun.business.domain.stock.StockInDetail;

public class AdjustStockEvent {

	private Department source;
	private Department destination;
	private Weapon weapon;
	private Set<StockInDetail> details = Sets.newHashSet();

	public Department getSource() {
		return source;
	}

	public void setSource(Department source) {
		this.source = source;
	}

	public Department getDestination() {
		return destination;
	}

	public void setDestination(Department destination) {
		this.destination = destination;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public Set<StockInDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<StockInDetail> details) {
		this.details = details;
	}

}
