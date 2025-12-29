package com.hsf.hsfbs.controller;


import com.hsf.hsfbs.core.annotations.Auth;
import com.hsf.hsfbs.core.annotations.AuthRoleId;
import com.hsf.hsfbs.core.annotations.AuthUserId;
import com.hsf.hsfbs.core.annotations.RestFulPack;
import com.hsf.hsfbs.core.component.UserComponent;
import com.hsf.hsfbs.core.exception.NotFoundException;
import com.hsf.hsfbs.core.exception.PermissionException;
import com.hsf.hsfbs.core.vo.PerformanceVo;
import com.hsf.hsfbs.constant.Rate;
import com.hsf.hsfbs.constant.RoleGrade;
import com.hsf.hsfbs.entity.*;
import com.hsf.hsfbs.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author yj
 * @date 2021-04-29 9:26
 */
@RestController
@RequestMapping("performance")
public class PerformanceController {

    private Logger logger = LoggerFactory.getLogger(PerformanceController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserComponent userComponent;

    @Autowired
    private PerformanceService performanceService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserDepartmentService userDepartmentService;

    @Auth
    @RestFulPack
    @PostMapping("create")
    public boolean create(@AuthUserId Integer adminId, @AuthRoleId Integer roleId, Integer userId, Rate rate, String remake) {
        Role adminRole = userComponent.getRoleByUserId(adminId);
        if (!adminRole.getId().equals(roleId)) {
            throw new PermissionException("操作人员权限不对等");
        }
        Role role = userComponent.getRoleByUserId(userId);
        if (adminRole.getRoleGrade().equals(RoleGrade.ADMIN)) {
            if (!role.getRoleGrade().equals(RoleGrade.MANAGER)) {
                throw new PermissionException("管理员只能给主管打分");
            }
        }
        if (adminRole.getRoleGrade().equals(RoleGrade.MANAGER)) {
            if (!role.getRoleGrade().equals(RoleGrade.USER)) {
                throw new PermissionException("主管只能给员工打分");
            }
            UserDepartment userDepartment = userDepartmentService.getByUserId(adminId);
            if (!userDepartmentService.verifyByDepartmentIdAndUserId(userDepartment.getDepartmentId(), userId)) {
                throw new PermissionException("这个员工不是我部门的人");
            }
        }
        Date date = new Date();
        String format = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String[] split = format.split("-");
        Performance performance = Performance.builder()
                .userId(userId)
                .rateTime(date)
                .year(split[0])
                .month(split[1])
                .day(split[2])
                .rate(rate)
                .remake(remake)
                .build();
        performanceService.save(performance);
        return true;
    }


    @Auth
    @RestFulPack
    @PostMapping("update")
    public boolean update(@AuthUserId Integer adminId, @AuthRoleId Integer roleId, Integer id, Rate rate, String remake) {
        Role role = userComponent.getRoleByUserId(adminId);
        if (!role.getId().equals(roleId)) {
            throw new PermissionException("只有部门管理才能绩效打分");
        }
        if (!role.getRoleGrade().equals(RoleGrade.MANAGER)) {
            throw new PermissionException("只有部门管理才能绩效打分");
        }
        Performance source = performanceService.get(id);
        source.setRate(rate);
        source.setRemake(remake);
        performanceService.update(source);
        return true;
    }


    @Auth
    @RestFulPack
    @GetMapping("paging")
    public Page<PerformanceVo> paging(@AuthUserId Integer adminId, @AuthRoleId Integer roleId, @PageableDefault(value = 20) Pageable pageable, String year, String month, String day) {
        if (month.length() < 2) {
            month = "0" + month;
        }
        Role adminRole = roleService.get(roleId);
        Page<Performance> page;
        if (adminRole.getRoleGrade().equals(RoleGrade.USER)) {
            page = performanceService.paging(pageable, Collections.singletonList(adminId), year, month, day);
        } else if (adminRole.getRoleGrade().equals(RoleGrade.MANAGER)) {
            Department department = userComponent.getDepartmentByUserId(adminId);
            List<UserDepartment> departmentList = userDepartmentService.listByDepartmentId(department.getId());
            List<Integer> idList = departmentList
                    .stream()
                    .map(it -> it.getUser().getId())
                    .collect(Collectors.toList());
            page = performanceService.paging(pageable, idList, year, month, day);
        } else {
            page = performanceService.paging(pageable, null, year, month, day);
        }
        List<PerformanceVo> list = page.getContent().stream().map(it -> {
            User user = exceptionDispose(it.getUserId());
            User createUser = exceptionDispose(it.getCreatedBy());
            User updateUser = exceptionDispose(it.getLastModifiedBy());
            Role userRole = userComponent.getRoleByUserId(user.getId());
            return new PerformanceVo(it, userRole.getName(), user.getName(), createUser.getName(), updateUser.getName());
        }).collect(Collectors.toList());
        return new PageImpl<PerformanceVo>(list, page.getPageable(), page.getTotalElements());
    }


    private User exceptionDispose(Integer userId) {
        try {
            return userService.get(userId);
            //这个叫捕获异常，根据异常去做对应的处理
        } catch (NotFoundException e) {
            return new User();
        }
    }

}