package io.jianxun.business.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

//报废提醒
@Entity
@Table(name = "wj_zb_rweaponnotices")
public class RWeaponNotice extends WeaponNotice {

	private static final long serialVersionUID = -7953155446321202172L;

}
