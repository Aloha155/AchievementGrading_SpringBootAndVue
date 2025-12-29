package com.hsf.hsfbs.dao;


import com.hsf.hsfbs.core.base.BaseDao;
import com.hsf.hsfbs.entity.UserDepartment;

import java.util.List;
import java.util.Optional;

/**
 * @author yj
 * @date 2021-04-27 11:10
 */
public interface UserDepartmentDao extends BaseDao<UserDepartment> {

    boolean existsByDepartmentIdAndUserId(Integer departmentId, Integer userId);

    Optional<UserDepartment> findByDepartmentIdAndUserId(Integer departmentId, Integer userId);

    Optional<UserDepartment> findByUserId( Integer userId);

    long countByDepartmentId(Integer departmentId);

    List<UserDepartment> findAllByDepartmentId(Integer departmentId);
}
