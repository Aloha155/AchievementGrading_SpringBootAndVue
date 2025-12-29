package com.bank.performance.service;


import com.bank.performance.core.base.BaseService;
import com.bank.performance.constant.BusinessState;
import com.bank.performance.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author yj
 * @date 2021-04-27 10:52
 */
public interface DepartmentService extends BaseService<Department> {

    Page<Department> paging(Pageable pageable, Integer id);

    Page<Department> paging(Pageable pageable, Integer id, BusinessState businessState, String code, String name);

    boolean verifyDepartment(Integer id);

    boolean verifyName(String name);
}
