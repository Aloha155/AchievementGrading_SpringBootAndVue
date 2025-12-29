package com.hsf.hsfbs.service.impl;


import com.hsf.hsfbs.core.base.BaseServiceImpl;
import com.hsf.hsfbs.core.exception.NotFoundException;
import com.hsf.hsfbs.constant.RoleGrade;
import com.hsf.hsfbs.dao.RoleDao;
import com.hsf.hsfbs.entity.Role;
import com.hsf.hsfbs.service.RoleService;
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