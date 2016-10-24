package io.jianxun.business.service;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import io.jianxun.business.domain.Department;
import io.jianxun.business.domain.User;
import io.jianxun.business.enums.BooleanStatus;
import io.jianxun.business.repository.DepartRepository;
import io.jianxun.business.web.dto.BaseTree;
import io.jianxun.business.web.dto.DepartmentTree;
import io.jianxun.common.utils.DepartLevelCodeSerialNumber;

@Service
@Transactional(readOnly = true)
public class DepartmentService extends TreeableEntityService<Department> {

	@Autowired
	private DepartLevelCodeSerialNumber serialNamer;
	@Autowired
	private UserDetailsService userDetailsService;

	public String getMaxLevelCode(String parenetLevelCode, int levelCodeLength) {
		String max = ((DepartRepository) entityRepository).findMaxLevelCode(parenetLevelCode + "%", levelCodeLength);
		return max;
	}

	@Override
	protected List<Department> getCurrentUserTreeData() {
		User user = userDetailsService.getCurrentUser();
		if (userDetailsService.isAdmin(user))
			return findAll();
		Department depart = user.getConstable().getDepart();
		return ((DepartRepository) entityRepository).findByLevelCodeStartingWith(depart.getLevelCode());
	}

	@Override
	protected List<? extends BaseTree> convertEntityToTree(List<Department> list) {
		List<DepartmentTree> tree = Lists.newArrayList();
		for (Department department : list) {
			DepartmentTree d = new DepartmentTree();
			d.setId(department.getId());
			d.setpId(department.getpId());
			d.setName(department.getSimpleName() == null ? department.getName() : department.getSimpleName());
			d.setLevelCode(department.getLevelCode());
			tree.add(d);
		}
		return tree;
	}

	@Transactional(readOnly = false)
	public Department createRoot() {
		Department root = new Department();
		root.setName("马鞍山市特警支队");
		root.setLevelCode("0000");
		root.setXxsx(1);
		entityRepository.saveAndFlush(root);
		return root;
	}

	@Override
	@Transactional(readOnly = false)
	public Department save(Department entity) {
		if (entityRepository.isNew(entity)) {
			Long pId = entity.getpId();
			if (pId == null || pId == -1L)
				throw new ServiceException("获取上级编码出错");
			Department parent = findOne(pId);
			if (parent == null)
				throw new ServiceException("获取上级编码出错");
			entity.setLevelCode(serialNamer.getSerialNumber(parent.getLevelCode()));
		}
		return super.save(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Department entity) {
		List<Department> children = ((DepartRepository) entityRepository)
				.findByLevelCodeStartingWith(entity.getLevelCode());
		for (Department department : children) {
			super.delete(department);
		}
		super.delete(entity);
	}

	@Transactional(readOnly = false)
	public void createTestData() {
		if (findAll().size() == 0) {
			Department root = createRoot();
			for (int i = 1; i < 5; i++) {
				Department d = new Department();
				d.setName("马鞍山市特警支队" + i + "大队");
				d.setpId(root.getId());
				d.setLevelCode(serialNamer.getSerialNumber(root.getLevelCode()));
				d.setCode("0" + i);
				d.setSimpleName(i + "大队");
				d.setXxsx(i);
				entityRepository.saveAndFlush(d);

			}

		}
	}

	public List<Department> findSubDepart(Department depart) {
		return ((DepartRepository) entityRepository).findByLevelCodeStartingWithAndLevelCodeNotAndDeleted(
				depart.getLevelCode(), depart.getLevelCode(), BooleanStatus.False);
	}

	public boolean isRoot(Department currentDepart) {
		return 1L == currentDepart.getId();
	}

}
