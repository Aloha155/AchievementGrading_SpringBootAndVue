package com.bank.performance.controller;


import com.bank.performance.core.annotations.Auth;
import com.bank.performance.core.annotations.AuthRoleId;
import com.bank.performance.core.annotations.AuthUserId;
import com.bank.performance.core.annotations.RestFulPack;
import com.bank.performance.core.component.UserComponent;
import com.bank.performance.core.exception.PermissionException;
import com.bank.performance.core.exception.ProgramException;
import com.bank.performance.core.util.RegexUtil;
import com.bank.performance.core.vo.DepartmentVo;
import com.bank.performance.core.vo.UserVo;
import com.bank.performance.constant.RoleGrade;
import com.bank.performance.entity.*;
import com.bank.performance.service.*;
import com.bank.performance.service.validation.DepartmentValidator;
import com.bank.performance.service.validation.UserValidator;
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

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author yj
 * @date 2021-04-27 10:50
 */
@RestController
@RequestMapping("department")
public class DepartmentController {

    private Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserDepartmentService userDepartmentService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;


    @Autowired
    private UserValidator userVerifyManage;

    @Autowired
    private DepartmentValidator departmentVerifyManage;

    @Autowired
    private UserComponent userComponent;


    @Auth
    @GetMapping("paging")
    @RestFulPack
    public Page<Department> paging(@AuthUserId Integer adminId, @PageableDefault(value = 20) Pageable pageable, Integer id) {
        return departmentService.paging(pageable, id);
    }


    @Auth
    @RestFulPack
    @GetMapping("pagingUserById")
    public Page<UserVo> listByDepartmentId(@AuthUserId Integer adminId, @AuthRoleId Integer roleId, @PageableDefault(value = 20) Pageable pageable, Integer id, String name, String phone) {
        Page<UserDepartment> page = userDepartmentService.paging(pageable, id, name, phone);
        List<UserVo> list = page.getContent().stream().map(it -> {
            User user = userService.get(it.getUser().getId());
            Address address = addressService.get(user.getAddresseeId());
            Role role = userComponent.getRoleByUserId(user.getId());
            Department department = userComponent.getDepartmentByUserId(user.getId());
            return new UserVo(user, address, role.getName(), department.getName());
        }).collect(Collectors.toList());
        return new PageImpl<UserVo>(list, page.getPageable(), page.getTotalElements());
    }

    @Auth
    @RestFulPack
    @PostMapping("create")
    public boolean create(@AuthUserId Integer adminId, @AuthRoleId Integer roleId, String name) {
        if (RegexUtil.checkStringIsNull(name)){
            throw new ProgramException("部门名称不能为空");
        }
        userVerifyManage.verifyUserRole(adminId, roleId);
        if (departmentVerifyManage.verifyName(name)) {
            throw new PermissionException("当前部门已经存在，无需重复添加");
        }
        departmentService.save(new Department(name));
        return true;
    }


    @Auth
    @RestFulPack
    @PostMapping("remove")
    public boolean remove(@AuthUserId Integer adminId, @AuthRoleId Integer roleId, Integer id) {
        userVerifyManage.verifyUserRole(adminId, roleId);
        if (userDepartmentService.contByDepartmentId(id) > 0) {
            throw new ProgramException("当前部门还有员工无法删除");
        }
        departmentService.delete(id);
        return true;
    }

    @Auth
    @RestFulPack
    @GetMapping("get")
    public Department get(@AuthUserId Integer adminId, @AuthRoleId Integer roleId, Integer id) {
        return departmentService.get(id);
    }

    @Auth
    @RestFulPack
    @PostMapping("updateName")
    public boolean updateName(@AuthUserId Integer adminId, @AuthRoleId Integer roleId, Integer id, String name) {
        userVerifyManage.verifyUserRole(adminId, roleId);
        Department source = departmentService.get(id);
        if (name.equals(source.getName())) {
            throw new PermissionException("名字一致不用修改");
        }
        source.setName(source.getName().equals(name) ? source.getName() : name);
        departmentService.update(source);
        return true;
    }

    @Auth
    @RestFulPack
    @PostMapping("updateUserDepartment")
    public boolean updateUserDepartment(@AuthUserId Integer adminId, @AuthRoleId Integer roleId, Integer sourceId, Integer userId, Integer targetId) {
        userVerifyManage.verifyUserRole(adminId, roleId);
        //检查员工
        User user = userService.get(userId);
        //原来的
        Department source = departmentService.get(sourceId);
        //目标的
        Department target = departmentService.get(targetId);
        //检查员工是否存在原部门下
        UserDepartment userDepartment = userDepartmentService.getByDepartmentIdAndUserId(source.getId(), user.getId());
        userDepartmentService.delete(userDepartment.getId());
        userDepartmentService.save(new UserDepartment(user, target.getId()));
        return true;
    }

    @Auth
    @RestFulPack
    @GetMapping("listAll")
    public List<DepartmentVo> listAll() {
        return departmentService.listAll().stream().map(DepartmentVo::new).collect(Collectors.toList());
    }

}