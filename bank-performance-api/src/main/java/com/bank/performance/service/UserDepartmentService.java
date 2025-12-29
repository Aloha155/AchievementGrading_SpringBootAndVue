package com.bank.performance.service;


import com.bank.performance.core.base.BaseService;
import com.bank.performance.entity.Department;
import com.bank.performance.entity.UserDepartment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author yj
 * @date 2021-04-27 11:10
 */
public interface UserDepartmentService extends BaseService<UserDepartment> {

    Page<UserDepartment> paging(Pageable pageable, Integer departmentId, String username, String phone);

    boolean verifyByDepartmentIdAndUserId(Integer departmentId, Integer userId);

    UserDepartment getByDepartmentIdAndUserId(Integer departmentId, Integer userId);

    UserDepartment getByUserId(Integer userId);

    long contByDepartmentId(Integer departmentId);

    List<UserDepartment> listByDepartmentId(Integer departmentId);
}
