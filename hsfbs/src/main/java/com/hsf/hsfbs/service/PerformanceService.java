package com.hsf.hsfbs.service;


import com.hsf.hsfbs.core.base.BaseService;
import com.hsf.hsfbs.entity.Performance;
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
