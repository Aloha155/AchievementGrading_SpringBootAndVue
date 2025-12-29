package com.bank.performance.dao;


import com.bank.performance.core.base.BaseDao;
import com.bank.performance.entity.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * @author yj
 * @date 2021-04-27 10:53
 */
public interface DepartmentDao extends BaseDao<Department> {

    boolean existsByName(String name);

}