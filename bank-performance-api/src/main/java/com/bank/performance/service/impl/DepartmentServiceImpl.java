package com.bank.performance.service.impl;


import com.bank.performance.core.base.BaseServiceImpl;
import com.bank.performance.core.util.RegexUtil;
import com.bank.performance.constant.BusinessState;
import com.bank.performance.dao.DepartmentDao;
import com.bank.performance.entity.Business;
import com.bank.performance.entity.Department;
import com.bank.performance.entity.DepartmentBusiness;
import com.bank.performance.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @date 2021-04-27 10:53
 */
@Service
public class DepartmentServiceImpl extends BaseServiceImpl<DepartmentDao, Department> implements DepartmentService {

    private Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Override
    public Page<Department> paging(Pageable pageable, Integer id) {
        Specification<Department> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (RegexUtil.checkObjectIsNotNull(id)) {
                predicates.add(criteriaBuilder.equal(root.get("id"), id));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return baseDao.findAll(specification, pageable);
    }

    @Override
    public Page<Department> paging(Pageable pageable, Integer id, BusinessState businessState, String code, String name) {
        Specification<Department> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (RegexUtil.checkObjectIsNotNull(id)) {
                predicates.add(criteriaBuilder.equal(root.get("id"), id));
            }
            Join<Department, Business> leftJoin = root.join("businessList", JoinType.LEFT);
            if (RegexUtil.checkObjectIsNotNull(businessState)) {
                predicates.add(criteriaBuilder.equal(leftJoin.get("state"), businessState));
            }
            if (RegexUtil.checkObjectIsNotNull(code)) {
                predicates.add(criteriaBuilder.like(leftJoin.get("code"), "%" + code + "%"));
            }
            if (RegexUtil.checkObjectIsNotNull(name)) {
                predicates.add(criteriaBuilder.like(leftJoin.get("name"), "%" + name + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return baseDao.findAll(specification, pageable);
    }

    @Override
    public boolean verifyDepartment(Integer id) {
        return baseDao.existsById(id);
    }

    @Override
    public boolean verifyName(String name) {
        return baseDao.existsByName(name);
    }
}