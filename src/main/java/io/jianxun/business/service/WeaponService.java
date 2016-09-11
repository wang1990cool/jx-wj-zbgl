package io.jianxun.business.service;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

import io.jianxun.business.domain.DataDictionary;
import io.jianxun.business.domain.Weapon;
import io.jianxun.business.repository.WeaponRepository;
import io.jianxun.business.web.dto.SelectOptionDto;
import io.jianxun.common.service.EntityService;
import io.jianxun.common.service.exception.ServiceException;

@Service
@Transactional(readOnly = true)
public class WeaponService extends EntityService<Weapon, Long> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.jianxun.common.service.EntityService#save(io.jianxun.common.domain.
	 * IdEntity)
	 */
	@Override
	@Transactional(readOnly = false)
	public Weapon save(Weapon entity) {
		if (entityRepository.isNew(entity)){
			entity.setCode(getNextCode());
			entity.setTypeCode(getNextTypeCode(entity.getName(), entity.getCategory()));
		}
		return super.save(entity);
	}

	public long countByName(String name) {
		return ((WeaponRepository) entityRepository).countByName(name);
	}

	public long countByNameAndId(String name, Long id) {
		return ((WeaponRepository) entityRepository).countByNameAndId(name, id);
	}

	public List<SelectOptionDto> getSelectData(List<Weapon> weapons) {
		List<SelectOptionDto> r = Lists.newArrayList();
		for (Weapon w : weapons) {
			r.add(convert2SelectDto(w));
		}
		return r;

	}

	public SelectOptionDto convert2SelectDto(Weapon weapon) {
		SelectOptionDto dto = new SelectOptionDto();
		dto.setCode(weapon.getId().toString());
		dto.setName(weapon.toString());
		return dto;

	}

	public String getMaxWeaponCode() {
		Weapon weapon = ((WeaponRepository) entityRepository).findTopByOrderByCodeDesc();
		if (weapon != null)
			return weapon.getCode();
		return "";
	}
	
	public String getNextCode(){
		String currentMax = getMaxWeaponCode();
		if (StringUtils.isEmpty(currentMax))
			return "00000001";
		else {
			DecimalFormat format = new DecimalFormat("00000000");
			try {
				int current = format.parse(currentMax).intValue();
				return format.format(current + 1);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("获取装备流水号错误！");
			}
		}
	}

	public String getMaxTypeCode(String name, DataDictionary category) {
		Weapon weapon = ((WeaponRepository) entityRepository).findTopByNameAndCategoryOrderByTypeCodeDesc(name,
				category);
		if (weapon != null)
			return weapon.getTypeCode();
		return "";
	}

	public String getNextTypeCode(String name, DataDictionary category) {
		String currentMax = getMaxTypeCode(name, category);
		if (StringUtils.isEmpty(currentMax))
			return "0001";
		else {
			DecimalFormat format = new DecimalFormat("0000");
			try {
				int current = format.parse(currentMax).intValue();
				return format.format(current + 1);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("获取类型流水号错误！");
			}
		}
	}

}
