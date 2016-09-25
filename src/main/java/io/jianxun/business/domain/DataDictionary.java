package io.jianxun.business.domain;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import io.jianxun.business.enums.DataCategory;

/**
 * 数据字典
 * 
 * @author tt
 *
 */
@Entity
@Table(name = "jx_zb_dataDics")
@EntityListeners(AuditingEntityListener.class)
public class DataDictionary extends BusinessBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 214211644452120954L;
	private String category = DataCategory.WEAPON.getCode();
	private String name;

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the name
	 */
	@NotEmpty(message = "{datadic.name.notnull}")
	@Size(min = 2, max = 10, message = "{datadic.name.size}")
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Transient
	public String getCategoryName() {
		if (StringUtils.isEmpty(this.getCategory()))
			return "";
		if (DataCategory.parse(this.getCategory()) == null)
			return "";
		return DataCategory.parse(this.getCategory()).getName();
	}

}
