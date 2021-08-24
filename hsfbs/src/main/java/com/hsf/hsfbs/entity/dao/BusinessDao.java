package com.hsf.hsfbs.entity.dao;


import com.hsf.hsfbs.core.base.BaseDao;
import com.hsf.hsfbs.entity.entity.Business;

/**
 * @author yj
 * @date 2021-04-29 13:57
 */
public interface BusinessDao extends BaseDao<Business> {

    boolean existsByCode(String code);

    boolean existsByName(String name);
}
