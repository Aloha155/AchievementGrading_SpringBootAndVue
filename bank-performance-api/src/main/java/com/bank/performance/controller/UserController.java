package com.bank.performance.controller;


import com.bank.performance.core.annotations.Auth;
import com.bank.performance.core.annotations.AuthRoleId;
import com.bank.performance.core.annotations.AuthUserId;
import com.bank.performance.core.annotations.RestFulPack;
import com.bank.performance.core.component.UserComponent;
import com.bank.performance.core.config.AccountConfig;
import com.bank.performance.core.dto.*;
import com.bank.performance.core.exception.NotFoundException;
import com.bank.performance.core.exception.PermissionException;
import com.bank.performance.core.exception.ProgramException;
import com.bank.performance.core.util.JwtUtil;
import com.bank.performance.core.util.Md5Util;
import com.bank.performance.core.vo.UserVo;
import com.bank.performance.constant.RoleGrade;
import com.bank.performance.constant.UserState;
import com.bank.performance.entity.*;
import com.bank.performance.service.*;
import com.bank.performance.service.validation.DepartmentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author yj
 * @date 2021-04-26 9:28
 */
@RestController
@RequestMapping("user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserDepartmentService userDepartmentService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private DepartmentValidator departmentManage;

    @Autowired
    private UserComponent userComponent;

    @Autowired
    private AccountConfig accountConfig;

    @Auth
    @RestFulPack
    @GetMapping("get")
    public UserVo get(@AuthUserId Integer id) {
        User user = userService.get(id);
        return getUserVo(user);
    }

    @Auth
    @RestFulPack
    @GetMapping("getByUserId")
    public UserVo getByUserId(@AuthUserId Integer id, Integer userId) {
        return getUserVo(userService.get(userId));
    }

    @Auth
    @RestFulPack
    @PostMapping("remove")
    public boolean remove(@AuthUserId Integer id, Integer userId) {
        User user = userService.get(userId);
        if (id.equals(user.getId())) {
            throw new PermissionException("自己不能删除自己");
        }
        Role role = userComponent.getRoleByUserId(id);
        if (!role.getRoleGrade().equals(RoleGrade.ADMIN)) {
            throw new PermissionException("不是管理员不能删除操作");
        }
        if (user.getUserState().equals(UserState.DISABLED)){
            throw new PermissionException("当前员工无法操作");
        }
//        UserRole userRole = userRoleService.getByUserId(user.getId());
//        userRoleService.delete(userRole.getId());
//        UserDepartment userDepartment = userDepartmentService.getByUserId(user.getId());
//        userDepartmentService.delete(userDepartment.getId());
        //我把这个代码逻辑改成逻辑删除，通过状态
        //员工信息还保留但是呢关联信息全部都会被删掉，避免出现哪个没有员工信息
        user.setUserState(UserState.DISABLED);
        userService.update(user);
        return true;
    }

    @RestFulPack
    @PostMapping("login")
    public UserVo login(LoginUserDto loginUserDto, HttpServletResponse response) {
        User user = userService.login(loginUserDto.getPhone(), Md5Util.Md5encryption(loginUserDto.getPassword(), accountConfig.getAccessTokenSecretKey()));
        if (user.getUserState().equals(UserState.DISABLED)){
            throw new PermissionException("员工账号无法被使用");
        }
        UserVo userVo = getUserVo(user);
        String token = JwtUtil.createJWT(user.getId(), accountConfig.getTokenMaxValidTime(), System.currentTimeMillis(), accountConfig.getAccessTokenSecretKey());
        response.addHeader("token", token);
        return userVo;
    }

    @Auth
    @RestFulPack
    @PostMapping("create")
    public UserVo create(@AuthUserId Integer id, @AuthRoleId Integer roleId, @RequestBody RegisterUserDto registerUserDto) {
        //检查关联角色
        Role role = roleService.get(registerUserDto.getRoleId());
        //检查部门
        //如果添加的是主管，则检查当前部门内是否已经存在主管
        Department department = departmentService.get(registerUserDto.getDepartmentId());
        if (RoleGrade.MANAGER.equals(role.getRoleGrade())) {
            if (departmentManage.verifyDepartmentManage(department.getId())) {
                throw new PermissionException("当前部门已经有主管了");
            }
        }
        //添加
        User user = userService.register(registerUserToUser(registerUserDto));
        //获取地址
        Address address = addressService.get(user.getAddresseeId());
        userRoleService.save(new UserRole(user.getId(), role.getId()));
        userDepartmentService.save(new UserDepartment(user, department.getId()));
        //
        return new UserVo(user, address, role.getName(), department.getName());
    }

    @Auth
    @RestFulPack
    @PostMapping("update")
    public UserVo update(@RequestBody UpdateUserDto updateUserDto) {
        User sourceUser = userService.get(updateUserDto.getId());
        Address sourceAddress = addressService.get(sourceUser.getAddresseeId());
        sourceUser.setAge(updateUserDto.getAge());
        sourceUser.setSex(updateUserDto.getSex());
        sourceUser.setBirthday(updateUserDto.getBirthday());
        sourceUser.setName(updateUserDto.getName());
        sourceUser.setPay(updateUserDto.getPay());
        AddressDto addressDto = updateUserDto.getAddressee();
        sourceAddress.setProvince(addressDto.getProvince());
        sourceAddress.setCity(addressDto.getCity());
        sourceAddress.setArea(addressDto.getArea());
        sourceAddress.setDetailedAddress(addressDto.getDetailedAddress());
        sourceAddress = addressService.update(sourceAddress);
        sourceUser.setAddresseeId(sourceAddress.getId());
        Role role = userComponent.getRoleByUserId(sourceUser.getId());
        Department department = userComponent.getDepartmentByUserId(sourceUser.getId());
        return new UserVo(userService.update(sourceUser), sourceAddress, role.getName(), department.getName());
    }

    @Auth
    @RestFulPack
    @PostMapping("updatePay")
    public boolean updatePay(@AuthUserId Integer id, @AuthRoleId Integer roleId, Integer userId, Integer pay) {
        User sourceUser = userService.get(userId);
        if (pay < 0) {
            throw new PermissionException("薪资无效");
        }
        Role role = userComponent.getRoleByUserId(userId);
        if (role.getRoleGrade().equals(RoleGrade.USER)) {
            if (pay > 1_000_00) {
                throw new PermissionException("薪资超出了，员工不可能这么高");
            }
        }
        if (pay.equals(sourceUser.getPay())) {
            throw new PermissionException("薪资一致，无需调整");
        }
        sourceUser.setPay(pay);
        userService.save(sourceUser);
        return true;
    }

    @Auth
    @RestFulPack
    @PostMapping("updatePassword")
    public boolean updatePassword(@AuthUserId Integer id, @RequestBody UpdatePasswordDto updatePasswordDto) {
        User sourceUser = userService.get(id);
        String oldPassword = Md5Util.Md5encryption(updatePasswordDto.getOldPassword(), accountConfig.getAccessTokenSecretKey());
        if (!oldPassword.equals(sourceUser.getPassword())) {
            throw new ProgramException("原密码不对，请重新尝试");
        }
        String newPassword = Md5Util.Md5encryption(updatePasswordDto.getNewPassword(), accountConfig.getAccessTokenSecretKey());
        if (oldPassword.equals(newPassword)) {
            throw new ProgramException("新旧密码一致无需修改");
        }
        sourceUser.setPassword(Md5Util.Md5encryption(updatePasswordDto.getNewPassword(), accountConfig.getAccessTokenSecretKey()));
        userService.update(sourceUser);

        return true;
    }

    private User registerUserToUser(RegisterUserDto registerUserDto) {
        User user = new User();
        user.setName(registerUserDto.getName());
        user.setPhone(registerUserDto.getPhone());
        user.setPassword(Md5Util.Md5encryption(registerUserDto.getPassword(), accountConfig.getAccessTokenSecretKey()));
        user.setAge(registerUserDto.getAge());
        user.setSex(registerUserDto.getSex());
        user.setEmail(registerUserDto.getEmail());
        user.setBirthday(registerUserDto.getBirthday());
        user.setAddresseeId(registerSaveAddress(registerUserDto.getAddressee()).getId());
        user.setPay(registerUserDto.getPay());
        return user;
    }

    private Address registerSaveAddress(AddressDto addressDto) {
        return addressService.save(
                new Address(
                        addressDto.getProvince(),
                        addressDto.getCity(),
                        addressDto.getArea(),
                        addressDto.getDetailedAddress()
                )
        );
    }

    private UserVo getUserVo(User user) {
        Address address = addressService.get(user.getAddresseeId());
        Role role = userComponent.getRoleByUserId(user.getId());
        String departmentName = "";
        try {
            departmentName = userComponent.getDepartmentByUserId(user.getId()).getName();
        } catch (NotFoundException e) {
            if (role.getRoleGrade().equals(RoleGrade.ADMIN)) {
                departmentName = "管理员";
            }
        }
        return new UserVo(user, address, role.getName(), departmentName);
    }

}