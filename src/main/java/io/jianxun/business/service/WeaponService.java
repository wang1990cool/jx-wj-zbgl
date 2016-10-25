package io.jianxun.business.service;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

import io.jianxun.business.domain.DataDictionary;
import io.jianxun.business.domain.Weapon;
import io.jianxun.business.enums.BooleanStatus;
import io.jianxun.business.event.RefreshStockDetailEvent;
import io.jianxun.business.repository.WeaponRepository;
import io.jianxun.business.web.dto.CodeNameDto;
import io.jianxun.common.service.exception.ServiceException;

@Service
@Transactional(readOnly = true)
public class WeaponService extends BusinessBaseEntityService<Weapon> {

	private final ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	public WeaponService(ApplicationEventPublisher applicationEventPublisher) {
		super();
		this.applicationEventPublisher = applicationEventPublisher;
	}

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
		boolean f = entityRepository.isNew(entity);
		if (f) {
			entity.setCode(getNextCode());
			entity.setTypeCode(getNextTypeCode(entity.getName(), entity.getCategory()));
		}
		super.save(entity);
		if (!f)
			applicationEventPublisher.publishEvent(new RefreshStockDetailEvent(entity));
		return entity;
	}

	public long countByNameAnd(String name) {
		return ((WeaponRepository) entityRepository).countByName(name);
	}

	public long countByNameAndId(String name, Long id) {
		return ((WeaponRepository) entityRepository).countByNameAndId(name, id);
	}

	public List<CodeNameDto> getSelectData(List<Weapon> weapons) {
		List<CodeNameDto> r = Lists.newArrayList();
		for (Weapon w : weapons) {
			r.add(convert2SelectDto(w));
		}
		return r;

	}

	public CodeNameDto convert2SelectDto(Weapon weapon) {
		CodeNameDto dto = new CodeNameDto();
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

	public String getNextCode() {
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

	public boolean validateNameAndTypeIsUnique(String name, String type, Long id) {
		Long count = 0L;
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(type))
			if (id != null && id != -1)
				count = ((WeaponRepository) this.entityRepository).countByNameAndTypeAndIdNotAndDeleted(name, type, id,
						BooleanStatus.False);
			else
				count = ((WeaponRepository) this.entityRepository).countByNameAndTypeAndDeleted(name, type,
						BooleanStatus.False);
		else if (id != null && id != -1)
			count = ((WeaponRepository) this.entityRepository).countByNameAndIdNotAndDeleted(name, id,
					BooleanStatus.False);
		else
			count = ((WeaponRepository) this.entityRepository).countByNameAndDeleted(name, BooleanStatus.False);
		return count == 0;
	}

}
