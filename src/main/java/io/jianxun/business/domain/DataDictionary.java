package io.jianxun.business.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.util.StringUtils;

import io.jianxun.business.enums.DataCategory;
import io.jianxun.common.domain.IdEntity;

/**
 * 数据字典
 * 
 * @author tt
 *
 */
@Entity
@Table(name = "jx_zb_dataDics")
public class DataDictionary extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 214211644452120954L;
	private String category = DataCategory.WEAPON.getCode();
	private String code;
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

	@Transient
	public String getCategoryName() {
		if (StringUtils.isEmpty(this.getCategory()))
			return "";
		if (DataCategory.parse(this.getCategory()) == null)
			return "";
		return DataCategory.parse(this.getCategory()).getName();
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
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

}
