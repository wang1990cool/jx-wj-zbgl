package io.jianxun.business.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import io.jianxun.common.domain.IdEntity;

/**
 * 跟机构信息相关的模型继承
 * 
 * @author tongtn
 *
 *         createDate: 2016-08-23
 */
@MappedSuperclass
public class DepartmentEntity extends IdEntity {

	private static final long serialVersionUID = 6595351983088428196L;

	// 所属单位Id
	@ManyToOne
	@JoinColumn(name = "depart_id")
	private Department depart;

	public Department getDepart() {
		return depart;
	}

	public void setDepart(Department depart) {
		this.depart = depart;
	}

}
