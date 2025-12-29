package com.bank.performance.service;


import com.bank.performance.core.base.BaseService;
import com.bank.performance.entity.Performance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author yj
 * @date 2021-04-29 9:11
 */
public interface PerformanceService extends BaseService<Performance> {

    Page<Performance> paging(Pageable pageable, List<Integer> userIdLIst, String year, String month, String day);
}
