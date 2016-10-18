package io.jianxun.business.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.jianxun.business.domain.stock.StockInDetail;

//维护提醒
@Entity
@Table(name = "wj_zb_weaponnotices")
public class WeaponNotice extends NoticeEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1422885035406271610L;

	private StockInDetail detail;

	/**
	 * @return the detail
	 */
	// 提醒关联的具体库存
	@ManyToOne
	@JoinColumn(name = "detail_id")
	public StockInDetail getDetail() {
		return detail;
	}

	/**
	 * @param detail
	 *            the detail to set
	 */
	public void setDetail(StockInDetail detail) {
		this.detail = detail;
	}

}
