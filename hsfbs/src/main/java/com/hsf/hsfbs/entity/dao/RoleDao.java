package com.hsf.hsfbs.entity.dao;


import com.hsf.hsfbs.core.base.BaseDao;
import com.hsf.hsfbs.entity.constant.RoleGrade;
import com.hsf.hsfbs.entity.entity.Role;

import java.util.Optional;

/**
 * @author yj
 * @date 2021-04-26 11:35
 */
public interface RoleDao extends BaseDao<Role> {

    Optional<Role> findByRoleGrade(RoleGrade roleGrade);
}
