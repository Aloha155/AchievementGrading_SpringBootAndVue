package com.bank.performance.dao;


import com.bank.performance.core.base.BaseDao;
import com.bank.performance.entity.Business;

/**
 * @author yj
 * @date 2021-04-29 13:57
 */
public interface BusinessDao extends BaseDao<Business> {

    boolean existsByCode(String code);

    boolean existsByName(String name);
}
