package com.hsf.hsfbs.service;


import com.hsf.hsfbs.core.base.BaseService;
import com.hsf.hsfbs.constant.BusinessState;
import com.hsf.hsfbs.entity.Department;
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
