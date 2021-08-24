package com.hsf.hsfbs.service.service;


import com.hsf.hsfbs.core.base.BaseService;
import com.hsf.hsfbs.entity.entity.Department;
import com.hsf.hsfbs.entity.entity.UserDepartment;
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
