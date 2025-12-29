package com.hsf.hsfbs.service;


import com.hsf.hsfbs.core.base.BaseDao;
import com.hsf.hsfbs.core.base.BaseService;
import com.hsf.hsfbs.constant.RoleGrade;
import com.hsf.hsfbs.entity.Role;

/**
 * @author yj
 * @date 2021-04-26 11:35
 */
public interface RoleService extends BaseService<Role> {

    Role getByRoleGrade(RoleGrade roleGrade);
}
