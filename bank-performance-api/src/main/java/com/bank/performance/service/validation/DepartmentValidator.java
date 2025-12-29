package com.bank.performance.service.validation;


import com.bank.performance.constant.RoleGrade;
import com.bank.performance.entity.Department;
import com.bank.performance.entity.Role;
import com.bank.performance.entity.UserDepartment;
import com.bank.performance.entity.UserRole;
import com.bank.performance.service.DepartmentService;
import com.bank.performance.service.RoleService;
import com.bank.performance.service.UserDepartmentService;
import com.bank.performance.service.UserRoleService;
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
public class DepartmentValidator {

    private Logger logger = LoggerFactory.getLogger(DepartmentValidator.class);

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