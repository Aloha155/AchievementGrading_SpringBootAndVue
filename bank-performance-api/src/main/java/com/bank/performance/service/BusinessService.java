package com.bank.performance.service;


import com.bank.performance.core.base.BaseService;
import com.bank.performance.constant.BusinessState;
import com.bank.performance.entity.Business;
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
