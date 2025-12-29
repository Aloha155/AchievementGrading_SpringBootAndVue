package com.bank.performance.service.impl;


import com.bank.performance.core.base.BaseServiceImpl;
import com.bank.performance.core.exception.NotFoundException;
import com.bank.performance.constant.RoleGrade;
import com.bank.performance.dao.RoleDao;
import com.bank.performance.entity.Role;
import com.bank.performance.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * @author yj
 * @date 2021-04-26 11:41
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleDao, Role> implements RoleService {

    private Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Override
    public Role getByRoleGrade(RoleGrade roleGrade) {
        return baseDao.findByRoleGrade(roleGrade).orElseThrow(()-> new NotFoundException("没有找到该权限"));
    }
}