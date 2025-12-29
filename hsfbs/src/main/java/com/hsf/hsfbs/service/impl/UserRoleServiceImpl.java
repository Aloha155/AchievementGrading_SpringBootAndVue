package com.hsf.hsfbs.service.impl;


import com.hsf.hsfbs.core.base.BaseServiceImpl;
import com.hsf.hsfbs.core.exception.NotFoundException;
import com.hsf.hsfbs.dao.UserRoleDao;
import com.hsf.hsfbs.entity.UserRole;
import com.hsf.hsfbs.service.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author yj
 * @date 2021-04-26 11:43
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleDao, UserRole> implements UserRoleService {

    private Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    @Override
    public UserRole getByUserId(Integer userId) {
        return baseDao.findByUserId(userId).orElseThrow(()->new NotFoundException("没有对应的信息"));
    }

    @Override
    public List<UserRole> listByRoleId(Integer roleId) {
        return baseDao.findAllByRoleId(roleId);
    }
}