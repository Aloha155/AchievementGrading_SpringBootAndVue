package com.hsf.hsfbs.service.impl;


import com.hsf.hsfbs.core.base.BaseServiceImpl;
import com.hsf.hsfbs.core.exception.NotFoundException;
import com.hsf.hsfbs.core.util.RegexUtil;
import com.hsf.hsfbs.constant.RoleGrade;
import com.hsf.hsfbs.constant.UserState;
import com.hsf.hsfbs.dao.UserDepartmentDao;
import com.hsf.hsfbs.entity.Role;
import com.hsf.hsfbs.entity.User;
import com.hsf.hsfbs.entity.UserDepartment;
import com.hsf.hsfbs.entity.UserRole;
import com.hsf.hsfbs.service.RoleService;
import com.hsf.hsfbs.service.UserDepartmentService;
import com.hsf.hsfbs.service.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


/**
 * @author yj
 * @date 2021-04-27 11:11
 */
@Service
public class UserDepartmentServiceImpl extends BaseServiceImpl<UserDepartmentDao, UserDepartment> implements UserDepartmentService {

    private Logger logger = LoggerFactory.getLogger(UserDepartmentServiceImpl.class);

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Override
    public Page<UserDepartment> paging(Pageable pageable, Integer departmentId, String username, String phone) {
        Specification<UserDepartment> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (RegexUtil.checkObjectIsNotNull(departmentId)) {
                predicates.add(criteriaBuilder.equal(root.get("departmentId"), departmentId));
            }
            Join<UserDepartment, User> leftJoin = root.join("user", JoinType.LEFT);
            if (RegexUtil.checkObjectIsNotNull(username)) {
                predicates.add(criteriaBuilder.like(leftJoin.get("name"), "%" + username + "%"));
            }
            if (RegexUtil.checkObjectIsNotNull(phone)) {
                predicates.add(criteriaBuilder.like(leftJoin.get("phone"), "%" + phone + "%"));
            }
            predicates.add(criteriaBuilder.equal(leftJoin.get("userState"), UserState.PASS));
            try{
                Role role = roleService.getByRoleGrade(RoleGrade.ADMIN);
                List<UserRole> userRoles = userRoleService.listByRoleId(role.getId());
                if (userRoles != null) {
                    userRoles.forEach(it -> predicates.add(criteriaBuilder.notEqual(leftJoin.get("id"), it.getUserId())));
                }
            }catch (NotFoundException e){
                logger.info("忽视当前错误");
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return baseDao.findAll(specification, pageable);
    }

    @Override
    public UserDepartment getByDepartmentIdAndUserId(Integer departmentId, Integer userId) {
        return baseDao.findByDepartmentIdAndUserId(departmentId, userId).orElseThrow(() -> new NotFoundException("该部门没有该员工"));
    }

    @Override
    public UserDepartment getByUserId(Integer userId) {
        return baseDao.findByUserId(userId).orElseThrow(() -> new NotFoundException("员工没有部门"));

    }

    @Override
    public long contByDepartmentId(Integer departmentId) {
        return baseDao.countByDepartmentId(departmentId);
    }

    @Override
    public List<UserDepartment> listByDepartmentId(Integer departmentId) {
        return baseDao.findAllByDepartmentId(departmentId);
    }

    @Override
    public boolean verifyByDepartmentIdAndUserId(Integer departmentId, Integer userId) {
        return baseDao.existsByDepartmentIdAndUserId(departmentId, userId);
    }
}