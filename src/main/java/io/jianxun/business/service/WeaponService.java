package io.jianxun.business.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import io.jianxun.business.domain.Weapon;
import io.jianxun.business.repository.WeaponRepository;
import io.jianxun.business.web.dto.SelectOptionDto;
import io.jianxun.common.service.EntityService;

@Service
@Transactional(readOnly = true)
public class WeaponService extends EntityService<Weapon, Long> {

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

}
