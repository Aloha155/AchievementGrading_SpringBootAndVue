package com.hsf.hsfbs.controller;


import com.hsf.hsfbs.core.annotations.Auth;
import com.hsf.hsfbs.core.annotations.AuthRoleId;
import com.hsf.hsfbs.core.annotations.AuthUserId;
import com.hsf.hsfbs.core.annotations.RestFulPack;
import com.hsf.hsfbs.core.component.UserComponent;
import com.hsf.hsfbs.core.config.AccountConfig;
import com.hsf.hsfbs.core.dto.*;
import com.hsf.hsfbs.core.exception.NotFoundException;
import com.hsf.hsfbs.core.exception.PermissionException;
import com.hsf.hsfbs.core.exception.ProgramException;
import com.hsf.hsfbs.core.util.JwtUtil;
import com.hsf.hsfbs.core.util.Md5Util;
import com.hsf.hsfbs.core.vo.UserVo;
import com.hsf.hsfbs.entity.constant.RoleGrade;
import com.hsf.hsfbs.entity.constant.UserState;
import com.hsf.hsfbs.entity.entity.*;
import com.hsf.hsfbs.service.service.*;
import com.hsf.hsfbs.service.verifyManage.DepartmentVerifyManage;
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
    private DepartmentVerifyManage departmentManage;

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
            throw new PermissionException("????????????????????????");
        }
        Role role = userComponent.getRoleByUserId(id);
        if (!role.getRoleGrade().equals(RoleGrade.ADMIN)) {
            throw new PermissionException("?????????????????????????????????");
        }
        if (user.getUserState().equals(UserState.DISABLED)){
            throw new PermissionException("????????????????????????");
        }
//        UserRole userRole = userRoleService.getByUserId(user.getId());
//        userRoleService.delete(userRole.getId());
//        UserDepartment userDepartment = userDepartmentService.getByUserId(user.getId());
//        userDepartmentService.delete(userDepartment.getId());
        //?????????????????????????????????????????????????????????
        //??????????????????????????????????????????????????????????????????????????????????????????????????????
        user.setUserState(UserState.DISABLED);
        userService.update(user);
        return true;
    }

    @RestFulPack
    @PostMapping("login")
    public UserVo login(LoginUserDto loginUserDto, HttpServletResponse response) {
        User user = userService.login(loginUserDto.getPhone(), Md5Util.Md5encryption(loginUserDto.getPassword(), accountConfig.getAccessTokenSecretKey()));
        if (user.getUserState().equals(UserState.DISABLED)){
            throw new PermissionException("???????????????????????????");
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
        //??????????????????
        Role role = roleService.get(registerUserDto.getRoleId());
        //????????????
        //???????????????????????????????????????????????????????????????????????????
        Department department = departmentService.get(registerUserDto.getDepartmentId());
        if (RoleGrade.MANAGER.equals(role.getRoleGrade())) {
            if (departmentManage.verifyDepartmentManage(department.getId())) {
                throw new PermissionException("??????????????????????????????");
            }
        }
        //??????
        User user = userService.register(registerUserToUser(registerUserDto));
        //????????????
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
            throw new PermissionException("????????????");
        }
        Role role = userComponent.getRoleByUserId(userId);
        if (role.getRoleGrade().equals(RoleGrade.USER)) {
            if (pay > 1_000_00) {
                throw new PermissionException("??????????????????????????????????????????");
            }
        }
        if (pay.equals(sourceUser.getPay())) {
            throw new PermissionException("???????????????????????????");
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
            throw new ProgramException("?????????????????????????????????");
        }
        String newPassword = Md5Util.Md5encryption(updatePasswordDto.getNewPassword(), accountConfig.getAccessTokenSecretKey());
        if (oldPassword.equals(newPassword)) {
            throw new ProgramException("??????????????????????????????");
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
                departmentName = "?????????";
            }
        }
        return new UserVo(user, address, role.getName(), departmentName);
    }

}