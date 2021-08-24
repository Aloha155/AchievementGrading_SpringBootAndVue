package com.hsf.hsfbs.controller;


import com.hsf.hsfbs.core.annotations.Auth;
import com.hsf.hsfbs.core.annotations.AuthRoleId;
import com.hsf.hsfbs.core.annotations.AuthUserId;
import com.hsf.hsfbs.core.annotations.RestFulPack;
import com.hsf.hsfbs.core.component.UserComponent;
import com.hsf.hsfbs.core.exception.PermissionException;
import com.hsf.hsfbs.core.exception.ProgramException;
import com.hsf.hsfbs.core.vo.BusinessVo;
import com.hsf.hsfbs.entity.constant.BusinessState;
import com.hsf.hsfbs.entity.constant.RoleGrade;
import com.hsf.hsfbs.entity.entity.Business;
import com.hsf.hsfbs.entity.entity.Department;
import com.hsf.hsfbs.entity.entity.Role;
import com.hsf.hsfbs.service.service.BusinessService;
import com.hsf.hsfbs.service.service.DepartmentBusinessService;
import com.hsf.hsfbs.service.service.DepartmentService;
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
 * @date 2021-04-29 14:00
 */
@RestController
@RequestMapping("business")
public class BusinessController {

    private Logger logger = LoggerFactory.getLogger(BusinessController.class);

    @Autowired
    private BusinessService businessService;

    @Autowired
    private DepartmentBusinessService departmentBusinessService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserComponent userComponent;

//    @Auth
//    @RestFulPack
//    @GetMapping("listAll")
//    public List<DepartmentBusinessVo> ListAll() {
//        List<Department> departments = departmentService.listAll();
//        return departments.stream().map(it -> {
//            List<DepartmentBusiness> departmentBusinessList = departmentBusinessService.listByDepartmentId(it.getId());
//            List<BusinessVo> businessList = departmentBusinessList.stream()
//                    .map(departmentBusiness ->
//                            new BusinessVo(businessService.get(departmentBusiness.getBusiness().getId())
//                            )
//                    ).collect(Collectors.toList());
//            return new DepartmentBusinessVo(it.getName(), businessList);
//        }).collect(Collectors.toList());
//    }


    @Auth
    @RestFulPack
    @GetMapping("paging")
    public Page<BusinessVo> paging(@PageableDefault(value = 20) Pageable pageable, Integer departmentId, BusinessState businessState, String code, String name) {
        Page<Business> page = businessService.paging(pageable, departmentId, businessState, code, name);
        List<BusinessVo> collect = page.getContent()
                .stream()
                .map(it -> new BusinessVo(it, it.getDepartment().getName()))
                .collect(Collectors.toList());
        return new PageImpl<BusinessVo>(collect, page.getPageable(), page.getTotalElements());
    }


    @Auth
    @RestFulPack
    @PostMapping("create")
    public boolean create(@AuthUserId Integer userId, @AuthRoleId Integer roleId, String name, String code, BusinessState businessState, Integer departmentId) {
        verifyRole(userId, roleId, code, name);
        Department department = departmentService.get(departmentId);
        Business business = businessService.save(new Business(name, code, businessState, department));
        department.getBusinessList().add(business);
        departmentService.update(department);
        return true;
    }

    @Auth
    @RestFulPack
    @PostMapping("update")
    public boolean update(@AuthUserId Integer userId, @AuthRoleId Integer roleId, Integer id, String name, String code) {
        Business source = businessService.get(id);
        if (!name.equals(source.getName())) {
            if (businessService.verifyByName(name)) {
                throw new ProgramException("当前名称已经存在");
            }
        }
        if (!code.equals(source.getCode())) {
            if (businessService.verifyByCode(code)) {
                throw new ProgramException("当前编码已经存在");
            }
        }
        verifyRole(userId, roleId, null, null);
        source.setName(name);
        source.setCode(code);
        businessService.update(source);
        return true;
    }

    @Auth
    @RestFulPack
    @GetMapping("get")
    public BusinessVo get(@AuthUserId Integer userId, @AuthRoleId Integer roleId, Integer id) {
        Business source = businessService.get(id);
        return new BusinessVo(source, source.getDepartment().getName());
    }

    @Auth
    @RestFulPack
    @PostMapping("updateState")
    public boolean update(@AuthUserId Integer userId, @AuthRoleId Integer roleId, Integer id, BusinessState businessState) {
        verifyRole(userId, roleId, null, null);
        Business source = businessService.get(id);
        source.setState(businessState);
        businessService.update(source);
        return true;
    }


    @Auth
    @RestFulPack
    @PostMapping("remove")
    public boolean remove(@AuthUserId Integer userId, @AuthRoleId Integer roleId, Integer id) {
        verifyRole(userId, roleId, null, null);
        Business source = businessService.get(id);
        return businessService.delete(source.getId());
    }


    private void verifyRole(Integer userId, Integer roleId, String code, String name) {
        Role role = userComponent.getRoleByUserId(userId);
        if (!role.getRoleGrade().equals(RoleGrade.ADMIN)) {
            throw new PermissionException("权限不够");
        }
        if (!role.getId().equals(roleId)) {
            throw new PermissionException("操作人员权限不对等");
        }
        if (code != null && businessService.verifyByCode(code)) {
            throw new PermissionException("当前code已经存在");
        }
        if (code != null && businessService.verifyByName(name)) {
            throw new PermissionException("当前name已经存在");
        }
    }
}