package com.hsf.hsfbs.dao;


import com.hsf.hsfbs.core.base.BaseDao;
import com.hsf.hsfbs.entity.UserRole;

import java.util.List;
import java.util.Optional;

/**
 * @author yj
 * @date 2021-04-26 11:35
 */
public interface UserRoleDao extends BaseDao<UserRole> {


    Optional<UserRole> findByUserId(Integer userId);


    List<UserRole> findAllByRoleId(Integer roleId);
}
