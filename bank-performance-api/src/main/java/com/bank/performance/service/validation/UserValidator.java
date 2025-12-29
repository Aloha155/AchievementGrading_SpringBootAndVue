package com.bank.performance.service.validation;


import com.bank.performance.core.exception.ProgramException;
import com.bank.performance.constant.RoleGrade;
import com.bank.performance.entity.Role;
import com.bank.performance.entity.UserRole;
import com.bank.performance.service.RoleService;
import com.bank.performance.service.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author yj
 * @date 2021-04-28 10:23
 */
@Component
public class UserValidator {

    private Logger logger = LoggerFactory.getLogger(UserValidator.class);

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;


    public void verifyUserRole(Integer userId, Integer roleId) {
        UserRole userRole = userRoleService.getByUserId(userId);
        if (!userRole.getRoleId().equals(roleId)) {
            throw new ProgramException("操作权限不相对");
        }
        Role role = roleService.get(roleId);
        if (!role.getRoleGrade().equals(RoleGrade.ADMIN)) {
            throw new ProgramException("管理员才能新增部门");
        }
    }
}