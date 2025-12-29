package com.bank.performance.controller;


import com.bank.performance.core.annotations.Auth;
import com.bank.performance.core.annotations.AuthRoleId;
import com.bank.performance.core.annotations.AuthUserId;
import com.bank.performance.core.annotations.RestFulPack;
import com.bank.performance.core.exception.ProgramException;
import com.bank.performance.entity.Role;
import com.bank.performance.entity.User;
import com.bank.performance.entity.UserRole;
import com.bank.performance.service.RoleService;
import com.bank.performance.service.UserRoleService;
import com.bank.performance.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author yj
 * @date 2021-04-27 9:57
 */
@RestController
@RequestMapping("role")
public class RoleController {

    private Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Auth
    @RestFulPack
    @PostMapping("updateRole")
    public boolean updateRole(@AuthUserId Integer adminId,Integer userId, Integer id) {
        User user = userService.get(userId);
        UserRole userRole = userRoleService.getByUserId(user.getId());
        Role role = roleService.get(id);
        if (id.equals(userRole.getRoleId())){
            throw new ProgramException("角色一致不用修改");
        }
        userRole.setRoleId(role.getId());
        userRoleService.update(userRole);
        return true;
    }


    @Auth
    @RestFulPack
    @GetMapping("listAll")
    public List<Role> listAll(){
        return roleService.listAll();
    }





}