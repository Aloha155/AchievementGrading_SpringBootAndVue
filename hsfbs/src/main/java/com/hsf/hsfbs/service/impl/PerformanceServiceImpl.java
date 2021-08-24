package com.hsf.hsfbs.service.impl;


import com.hsf.hsfbs.core.base.BaseServiceImpl;
import com.hsf.hsfbs.core.util.RegexUtil;
import com.hsf.hsfbs.entity.dao.PerformanceDao;
import com.hsf.hsfbs.entity.entity.Performance;
import com.hsf.hsfbs.service.service.PerformanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


/**
 * @author yj
 * @date 2021-04-29 9:13
 */
@Service
public class PerformanceServiceImpl extends BaseServiceImpl<PerformanceDao, Performance> implements PerformanceService {

    private Logger logger = LoggerFactory.getLogger(PerformanceServiceImpl.class);


    @Override
    public Page<Performance> paging(Pageable pageable, List<Integer> userIdLIst, String year, String month, String day) {
        Specification<Performance> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (RegexUtil.checkObjectIsNotNull(userIdLIst)) {
                CriteriaBuilder.In<Integer> list = criteriaBuilder.in(root.get("userId"));
                userIdLIst.forEach(list::value);
                predicates.add(list);
            }
            if (RegexUtil.checkStringIsNotNull(year)) {
                predicates.add(criteriaBuilder.equal(root.get("year"), year));
            }
            if (RegexUtil.checkStringIsNotNull(month)) {
                predicates.add(criteriaBuilder.equal(root.get("month"), month));
            }
            if (RegexUtil.checkStringIsNotNull(day)) {
                predicates.add(criteriaBuilder.equal(root.get("day"), day));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        return baseDao.findAll(specification, pageable);
    }
}