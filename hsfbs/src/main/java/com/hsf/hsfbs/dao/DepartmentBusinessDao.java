package com.hsf.hsfbs.dao;


import com.hsf.hsfbs.core.base.BaseDao;
import com.hsf.hsfbs.entity.DepartmentBusiness;

import java.util.List;
import java.util.Optional;

/**
 * @author yj
 * @date 2021-04-29 14:26
 */
public interface DepartmentBusinessDao extends BaseDao<DepartmentBusiness> {

    Optional<DepartmentBusiness> findByDepartmentId(Integer departmentId);
}
