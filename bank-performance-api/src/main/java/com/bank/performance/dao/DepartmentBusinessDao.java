package com.bank.performance.dao;


import com.bank.performance.core.base.BaseDao;
import com.bank.performance.entity.DepartmentBusiness;

import java.util.List;
import java.util.Optional;

/**
 * @author yj
 * @date 2021-04-29 14:26
 */
public interface DepartmentBusinessDao extends BaseDao<DepartmentBusiness> {

    Optional<DepartmentBusiness> findByDepartmentId(Integer departmentId);
}
