package io.jianxun.common.web;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jianxun.common.repository.EntityRepository;

public class EntityController<T, ID extends Serializable> {

	protected final EntityRepository<T, ID> entityRepository;

	@Autowired
	public EntityController(EntityRepository<T, ID> entityRepository) {
		Assert.notNull(entityRepository, "Repository must not be null!");
		this.entityRepository = entityRepository;
	}

	@RequestMapping(value = { "", "/page" })
	public String page(Model model, Pageable pageable, HttpRequest request) {
		Page<T> page = entityRepository.findAll(pageable);
		model.addAttribute("content", page.getContent());
		model.addAttribute("pageNum", pageable.getPageNumber());
		model.addAttribute("pageSize", pageable.getPageSize());
		return "page";
	}

}
