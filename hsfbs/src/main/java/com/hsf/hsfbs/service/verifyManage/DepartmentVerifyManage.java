package com.hsf.hsfbs.service.verifyManage;


import com.hsf.hsfbs.entity.constant.RoleGrade;
import com.hsf.hsfbs.entity.entity.Department;
import com.hsf.hsfbs.entity.entity.Role;
import com.hsf.hsfbs.entity.entity.UserDepartment;
import com.hsf.hsfbs.entity.entity.UserRole;
import com.hsf.hsfbs.service.service.DepartmentService;
import com.hsf.hsfbs.service.service.RoleService;
import com.hsf.hsfbs.service.service.UserDepartmentService;
import com.hsf.hsfbs.service.service.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author yj
 * @date 2021-04-28 10:25
 */
@Component
public class DepartmentVerifyManage {

    private Logger logger = LoggerFactory.getLogger(DepartmentVerifyManage.class);

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserDepartmentService userDepartmentService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    public boolean verifyDepartmentManage(Integer departmentId){
        Department department = departmentService.get(departmentId);
        List<UserDepartment> userDepartments = userDepartmentService.listByDepartmentId(department.getId());
        for (UserDepartment userDepartment : userDepartments) {
            UserRole userRole = userRoleService.getByUserId(userDepartment.getUser().getId());
            Role role = roleService.get(userRole.getRoleId());
            if (role.getRoleGrade().equals(RoleGrade.MANAGER)){
                return true;
            }
        }
        return false;
    }

    public boolean verifyName(String name){
        return departmentService.verifyName(name);
    }
}