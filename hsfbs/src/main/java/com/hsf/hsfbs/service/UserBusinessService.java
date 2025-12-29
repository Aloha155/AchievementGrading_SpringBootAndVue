package com.hsf.hsfbs.service;


import com.hsf.hsfbs.core.base.BaseService;
import com.hsf.hsfbs.constant.UserBusinessState;
import com.hsf.hsfbs.entity.UserBusiness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author yj
 * @date 2021-05-07 9:46
 */
public interface UserBusinessService extends BaseService<UserBusiness> {

    Page<UserBusiness> paging(Pageable pageable, List<Integer> idList, Integer departmentId, String businessName,String businessCode, String username, String phone, UserBusinessState userBusinessState);
}
