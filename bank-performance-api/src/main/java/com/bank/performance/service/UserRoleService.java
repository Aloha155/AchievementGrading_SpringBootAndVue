package com.bank.performance.service;


import com.bank.performance.core.base.BaseDao;
import com.bank.performance.core.base.BaseService;
import com.bank.performance.entity.UserRole;

import java.util.List;

/**
 * @author yj
 * @date 2021-04-26 11:35
 */
public interface UserRoleService extends BaseService<UserRole> {

    UserRole getByUserId(Integer userId);

    List<UserRole> listByRoleId(Integer roleId);

}
