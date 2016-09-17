package io.jianxun.business.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import io.jianxun.business.domain.DataDictionary;
import io.jianxun.business.repository.DataDicRepository;
import io.jianxun.business.web.dto.SelectOptionDto;

@Service
@Transactional(readOnly = true)
public class DataDicService extends BusinessBaseEntityService<DataDictionary> {

	public List<DataDictionary> findByCategory(String category) {
		return ((DataDicRepository) entityRepository).findByCategory(category);
	}

	public List<SelectOptionDto> getSelectOptions(List<DataDictionary> data) {
		List<SelectOptionDto> options = Lists.newArrayList();
		for (DataDictionary u : data) {
			SelectOptionDto e = convert2SelectOption(u);
			options.add(e);
		}
		return options;
	}

	private SelectOptionDto convert2SelectOption(DataDictionary u) {
		SelectOptionDto s = new SelectOptionDto();
		s.setCode(u.getId().toString());
		s.setName(u.getName());
		return s;
	}

}
