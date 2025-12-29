package com.bank.performance.service.impl;


import com.bank.performance.core.base.BaseServiceImpl;
import com.bank.performance.core.exception.NotFoundException;
import com.bank.performance.dao.UserRoleDao;
import com.bank.performance.entity.UserRole;
import com.bank.performance.service.UserRoleService;
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