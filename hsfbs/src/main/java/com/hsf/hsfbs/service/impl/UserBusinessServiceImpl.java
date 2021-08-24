package com.hsf.hsfbs.service.impl;


import com.hsf.hsfbs.core.base.BaseServiceImpl;
import com.hsf.hsfbs.core.util.RegexUtil;
import com.hsf.hsfbs.entity.constant.UserBusinessState;
import com.hsf.hsfbs.entity.dao.UserBusinessDao;
import com.hsf.hsfbs.entity.entity.UserBusiness;
import com.hsf.hsfbs.service.service.UserBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


/**
 * @author yj
 * @date 2021-05-07 9:47
 */

@Service
public class UserBusinessServiceImpl extends BaseServiceImpl<UserBusinessDao, UserBusiness> implements UserBusinessService {

    private Logger logger = LoggerFactory.getLogger(UserBusinessServiceImpl.class);

    @Override
    public Page<UserBusiness> paging(Pageable pageable, List<Integer> idList, Integer departmentId, String businessName, String businessCode, String username, String phone, UserBusinessState userBusinessState) {
        Specification<UserBusiness> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (RegexUtil.checkObjectIsNotNull(idList)) {
                for (Integer userId : idList) {
                    predicates.add(criteriaBuilder.equal(root.get("userId"), userId));
                }
            }
            if (RegexUtil.checkObjectIsNotNull(departmentId)) {
                predicates.add(criteriaBuilder.equal(root.get("departmentId"), departmentId));
            }
            if (RegexUtil.checkObjectIsNotNull(businessName)) {
                predicates.add(criteriaBuilder.like(root.get("businessName"), "%" + businessName + "%"));
            }
            if (RegexUtil.checkObjectIsNotNull(businessCode)) {
                predicates.add(criteriaBuilder.like(root.get("businessCode"), "%" + businessCode + "%"));
            }
            if (RegexUtil.checkObjectIsNotNull(username)) {
                predicates.add(criteriaBuilder.like(root.get("username"), "%" + username + "%"));
            }
            if (RegexUtil.checkObjectIsNotNull(phone)) {
                predicates.add(criteriaBuilder.like(root.get("phone"), "%" + phone + "%"));
            }
            if (RegexUtil.checkObjectIsNotNull(userBusinessState)) {
                predicates.add(criteriaBuilder.equal(root.get("userBusinessState"), userBusinessState));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return baseDao.findAll(specification, pageable);
    }
}