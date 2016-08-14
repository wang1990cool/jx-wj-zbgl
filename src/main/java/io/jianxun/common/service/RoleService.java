package io.jianxun.common.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jianxun.common.domain.user.Role;

@Service
@Transactional(readOnly = true)
public class RoleService extends EntityService<Role, Long> {

}
