package com.hsf.hsfbs.service.service;


import com.hsf.hsfbs.core.base.BaseService;
import com.hsf.hsfbs.entity.constant.BusinessState;
import com.hsf.hsfbs.entity.entity.DepartmentBusiness;
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
