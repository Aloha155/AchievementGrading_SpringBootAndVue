package com.bank.performance.service.impl;


import com.bank.performance.core.base.BaseServiceImpl;
import com.bank.performance.core.exception.NotFoundException;
import com.bank.performance.core.util.RegexUtil;
import com.bank.performance.constant.BusinessState;
import com.bank.performance.constant.RoleGrade;
import com.bank.performance.dao.DepartmentBusinessDao;
import com.bank.performance.entity.*;
import com.bank.performance.service.DepartmentBusinessService;
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
 * @date 2021-04-29 14:26
 */
@Service
public class DepartmentBusinessServiceImpl extends BaseServiceImpl<DepartmentBusinessDao, DepartmentBusiness> implements DepartmentBusinessService {

    private Logger logger = LoggerFactory.getLogger(DepartmentBusinessServiceImpl.class);


    @Override
    public DepartmentBusiness getByDepartmentId(Integer departmentId) {
        return baseDao.findByDepartmentId(departmentId).orElseThrow(()-> new NotFoundException("没有找到"));
    }

    @Override
    public Page<DepartmentBusiness> paging(Pageable pageable, Integer departmentId, BusinessState businessState, String code, String name) {
        return null;
    }
}