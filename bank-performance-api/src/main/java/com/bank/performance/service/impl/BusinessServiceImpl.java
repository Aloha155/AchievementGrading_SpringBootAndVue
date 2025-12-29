package com.bank.performance.service.impl;


import com.bank.performance.core.base.BaseServiceImpl;
import com.bank.performance.core.util.RegexUtil;
import com.bank.performance.constant.BusinessState;
import com.bank.performance.dao.BusinessDao;
import com.bank.performance.entity.Business;
import com.bank.performance.entity.Department;
import com.bank.performance.service.BusinessService;
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
 * @date 2021-04-29 13:58
 */
@Service
public class BusinessServiceImpl extends BaseServiceImpl<BusinessDao, Business> implements BusinessService {

    private Logger logger = LoggerFactory.getLogger(BusinessServiceImpl.class);

    @Override
    public boolean verifyByCode(String code) {
        return baseDao.existsByCode(code);
    }

    @Override
    public boolean verifyByName(String name) {
        return baseDao.existsByName(name);
    }

    @Override
    public Page<Business> paging(Pageable pageable, Integer departmentId, BusinessState businessState, String code, String name) {
        Specification<Business> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (RegexUtil.checkObjectIsNotNull(businessState)) {
                predicates.add(criteriaBuilder.equal(root.get("state"), businessState));
            }
            if (RegexUtil.checkObjectIsNotNull(code)) {
                predicates.add(criteriaBuilder.like(root.get("code"), "%" + code + "%"));
            }
            if (RegexUtil.checkObjectIsNotNull(name)) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            Join<Business, Department> leftJoin = root.join("department", JoinType.LEFT);
            if (RegexUtil.checkObjectIsNotNull(departmentId)) {
                predicates.add(criteriaBuilder.equal(leftJoin.get("id"), departmentId));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return baseDao.findAll(specification, pageable);
    }
}