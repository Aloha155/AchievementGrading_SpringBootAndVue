package com.bank.performance.service;


import com.bank.performance.core.base.BaseDao;
import com.bank.performance.core.base.BaseService;
import com.bank.performance.constant.RoleGrade;
import com.bank.performance.entity.Role;

/**
 * @author yj
 * @date 2021-04-26 11:35
 */
public interface RoleService extends BaseService<Role> {

    Role getByRoleGrade(RoleGrade roleGrade);
}
