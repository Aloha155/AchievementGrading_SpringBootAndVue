package com.hsf.hsfbs.service.service;


import com.hsf.hsfbs.core.base.BaseDao;
import com.hsf.hsfbs.core.base.BaseService;
import com.hsf.hsfbs.entity.entity.UserRole;

import java.util.List;

/**
 * @author yj
 * @date 2021-04-26 11:35
 */
public interface UserRoleService extends BaseService<UserRole> {

    UserRole getByUserId(Integer userId);

    List<UserRole> listByRoleId(Integer roleId);

}
