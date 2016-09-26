package io.jianxun.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.business.domain.Role;

@Service
@Transactional(readOnly = true)
public class RoleService extends BusinessBaseEntityService<Role> {

}
