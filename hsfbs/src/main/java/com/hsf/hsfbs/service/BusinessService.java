package com.hsf.hsfbs.service;


import com.hsf.hsfbs.core.base.BaseService;
import com.hsf.hsfbs.constant.BusinessState;
import com.hsf.hsfbs.entity.Business;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author yj
 * @date 2021-04-29 13:57
 */
public interface BusinessService extends BaseService<Business> {

    boolean verifyByCode(String code);

    boolean verifyByName(String name);

    Page<Business> paging(Pageable pageable, Integer departmentId, BusinessState businessState, String code, String name);
    
}
