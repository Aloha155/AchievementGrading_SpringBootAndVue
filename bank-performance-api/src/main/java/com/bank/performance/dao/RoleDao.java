package com.bank.performance.dao;


import com.bank.performance.core.base.BaseDao;
import com.bank.performance.constant.RoleGrade;
import com.bank.performance.entity.Role;

import java.util.Optional;

/**
 * @author yj
 * @date 2021-04-26 11:35
 */
public interface RoleDao extends BaseDao<Role> {

    Optional<Role> findByRoleGrade(RoleGrade roleGrade);
}
