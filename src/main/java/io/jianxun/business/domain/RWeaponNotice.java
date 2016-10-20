package io.jianxun.business.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.jianxun.business.domain.stock.StockInDetail;

//报废提醒
@Entity
@Table(name = "wj_zb_rweaponnotices")
public class RWeaponNotice extends NoticeEntity {

	private static final long serialVersionUID = -7953155446321202172L;

	
	private StockInDetail detail;


	/**
	 * @return the detail
	 */
	@ManyToOne
	@JoinColumn(name = "detail_id")
	public StockInDetail getDetail() {
		return detail;
	}


	/**
	 * @param detail the detail to set
	 */
	public void setDetail(StockInDetail detail) {
		this.detail = detail;
	}
	
	
	
}
