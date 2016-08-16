package io.jianxun.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.Weapon;
import io.jianxun.business.repository.WeaponRepository;
import io.jianxun.common.service.EntityService;

@Service
@Transactional(readOnly = true)
public class WeaponService extends EntityService<Weapon, Long> {
	
	public long countByName(String name){
		return ((WeaponRepository)entityRepository).countByName(name);
	}
	
	public long countByNameAndId(String name,Long id){
		return ((WeaponRepository)entityRepository).countByNameAndId(name, id);
	}

}
