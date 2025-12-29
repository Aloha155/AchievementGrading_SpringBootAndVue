package com.bank.performance.service;


import com.bank.performance.core.base.BaseService;
import com.bank.performance.constant.BusinessState;
import com.bank.performance.entity.DepartmentBusiness;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author yj
 * @date 2021-04-29 14:26
 */
public interface DepartmentBusinessService extends BaseService<DepartmentBusiness> {

    DepartmentBusiness getByDepartmentId(Integer departmentId);

    Page<DepartmentBusiness> paging(Pageable pageable, Integer departmentId, BusinessState businessState, String code, String name);
}
