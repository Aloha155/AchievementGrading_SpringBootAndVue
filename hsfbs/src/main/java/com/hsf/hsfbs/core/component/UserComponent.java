package com.hsf.hsfbs.core.component;


import com.hsf.hsfbs.entity.Department;
import com.hsf.hsfbs.entity.Role;
import com.hsf.hsfbs.entity.UserDepartment;
import com.hsf.hsfbs.entity.UserRole;
import com.hsf.hsfbs.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author yj
 * @date 2021-04-28 11:59
 */
@Component
public class UserComponent {

    private Logger logger = LoggerFactory.getLogger(UserComponent.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserDepartmentService userDepartmentService;

    @Autowired
    private DepartmentService departmentService;

    public Role getRoleByUserId(Integer userId) {
        UserRole userRole = userRoleService.getByUserId(userId);
        return roleService.get(userRole.getRoleId());
    }


    public Department getDepartmentByUserId(Integer userId) {
        UserDepartment userDepartment = userDepartmentService.getByUserId(userId);
        return departmentService.get(userDepartment.getDepartmentId());
    }
}