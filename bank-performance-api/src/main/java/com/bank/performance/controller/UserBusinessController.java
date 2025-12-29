package com.bank.performance.controller;


import com.bank.performance.core.annotations.Auth;
import com.bank.performance.core.annotations.AuthRoleId;
import com.bank.performance.core.annotations.AuthUserId;
import com.bank.performance.core.annotations.RestFulPack;
import com.bank.performance.core.component.UserComponent;
import com.bank.performance.core.dto.UserBusinessDto;
import com.bank.performance.core.exception.PermissionException;
import com.bank.performance.core.util.RegexUtil;
import com.bank.performance.constant.RoleGrade;
import com.bank.performance.constant.UserBusinessState;
import com.bank.performance.entity.*;
import com.bank.performance.service.BusinessService;
import com.bank.performance.service.DepartmentService;
import com.bank.performance.service.UserBusinessService;
import com.bank.performance.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author yj
 * @date 2021-05-07 9:47
 */
@RestController
@RequestMapping("userBusiness")
public class UserBusinessController {

    private Logger logger = LoggerFactory.getLogger(UserBusinessController.class);

    private final UserService userService;

    private final UserComponent userComponent;

    private final BusinessService businessService;

    private final DepartmentService departmentService;

    private final UserBusinessService userBusinessService;

    @Autowired
    public UserBusinessController(UserService userService,
                                  UserComponent userComponent,
                                  BusinessService businessService,
                                  DepartmentService departmentService,
                                  UserBusinessService userBusinessService) {
        this.userService = userService;
        this.userComponent = userComponent;
        this.businessService = businessService;
        this.departmentService = departmentService;
        this.userBusinessService = userBusinessService;
    }

    @Auth
    @RestFulPack
    @PostMapping("create")
    private boolean create(@AuthUserId Integer adminId, @AuthRoleId Integer roleId, Integer businessId, String phone, String username, String remake) {
        Role role = userComponent.getRoleByUserId(adminId);
        if (!role.getRoleGrade().equals(RoleGrade.USER)) {
            throw new PermissionException("只有员工才能办理业务");
        }
        User user = userService.get(adminId);
        Department department = userComponent.getDepartmentByUserId(adminId);
        Business business = businessService.get(businessId);
        if (!business.getDepartment().getId().equals(department.getId())) {
            throw new PermissionException("该员工无法办理改业务");
        }
        userBusinessService.save(new UserBusiness(adminId, user.getName(), user.getPhone(), department.getId(), department.getName(), businessId, business.getName(), business.getCode(), username, phone, remake, UserBusinessState.CREATE));
        return true;
    }

    @Auth
    @RestFulPack
    @PostMapping("paging")
    private Page<UserBusiness> paging(@AuthUserId Integer adminId, @AuthRoleId Integer roleId, @PageableDefault(value = 20) Pageable pageable, @RequestBody UserBusinessDto userBusinessDto) {
        Role role = userComponent.getRoleByUserId(adminId);
        List<Integer> idList = new ArrayList<>();
        if (role.getRoleGrade().equals(RoleGrade.USER)) {
            idList.add(adminId);
        } else {
            if (RegexUtil.checkStringIsNotNull(userBusinessDto.getAdminName()) || RegexUtil.checkStringIsNotNull(userBusinessDto.getAdminPhone())) {
                List<User> users = userService.listByLikeUsernameAndPhone(userBusinessDto.getAdminName(), userBusinessDto.getAdminPhone());
                System.out.println(users.toString());
                idList = users
                        .stream()
                        .map(User::getId)
                        .collect(Collectors.toList());
            }
        }
        return userBusinessService.paging(
                pageable,
                idList,
                userBusinessDto.getDepartmentId(),
                userBusinessDto.getBusinessName(),
                userBusinessDto.getBusinessCode(),
                userBusinessDto.getUsername(),
                userBusinessDto.getPhone(),
                userBusinessDto.getUserBusinessStat());
    }

    @Auth
    @RestFulPack
    @GetMapping("get")
    private UserBusiness paging(@AuthUserId Integer adminId, @AuthRoleId Integer roleId, Integer id) {
        return userBusinessService.get(id);
    }

    @Auth
    @RestFulPack
    @PostMapping("createAudit")
    private boolean createAudit(@AuthUserId Integer adminId, @AuthRoleId Integer roleId, Integer id, String cause) {
        UserBusiness userBusiness = userBusinessService.get(id);
        if (!userBusiness.getUserBusinessState().equals(UserBusinessState.CREATE)) {
            throw new PermissionException("只有刚创建的业务支持申请修改");
        }
        userBusiness.setUserBusinessState(UserBusinessState.UPDATE);
        userBusiness.setAuditCause(cause);
        userBusinessService.update(userBusiness);
        return true;
    }

    @Auth
    @RestFulPack
    @PostMapping("updateState")
    private boolean updateState(@AuthUserId Integer adminId, @AuthRoleId Integer roleId, Integer id, UserBusinessState userBusinessState) {
        UserBusiness userBusiness = userBusinessService.get(id);
        if (!userBusiness.getUserBusinessState().equals(UserBusinessState.CREATE) && !userBusiness.getUserBusinessState().equals(UserBusinessState.UPDATE_SUCCESS)) {
            throw new PermissionException("当前状态无法支持完成与结束");
        }
        userBusiness.setUserBusinessState(userBusinessState);
        userBusinessService.update(userBusiness);
        return true;
    }

    @Auth
    @RestFulPack
    @PostMapping("update")
    private boolean update(@AuthUserId Integer adminId, @AuthRoleId Integer roleId, Integer id, String username, String phone) {
        UserBusiness userBusiness = userBusinessService.get(id);
        if (!userBusiness.getUserBusinessState().equals(UserBusinessState.UPDATE)) {
            throw new PermissionException("只有提交了修改才能修改相应的信息");
        }
        if (RegexUtil.checkObjectIsNull(username) || RegexUtil.checkObjectIsNull(phone)) {
            throw new PermissionException("参数不能为空");
        }
        userBusiness.setUsername(username);
        userBusiness.setPhone(phone);
        userBusiness.setUserBusinessState(UserBusinessState.UPDATE_SUCCESS);
        userBusinessService.update(userBusiness);
        return true;
    }

    @Auth
    @RestFulPack
    @PostMapping("audit")
    private boolean audit(@AuthUserId Integer adminId, @AuthRoleId Integer roleId, Integer id, UserBusinessState userBusinessState, String auditRemake) {
        UserBusiness userBusiness = userBusinessService.get(id);
        if (!userBusiness.getUserBusinessState().equals(UserBusinessState.UPDATE)) {
            throw new PermissionException("只有刚创建的业务支持申请修改");
        }
        userBusiness.setUserBusinessState(userBusinessState);
        userBusiness.setAuditRemake(auditRemake);
        userBusinessService.update(userBusiness);
        return true;
    }

}