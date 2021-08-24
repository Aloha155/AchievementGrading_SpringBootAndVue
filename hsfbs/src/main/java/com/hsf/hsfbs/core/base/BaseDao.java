package com.hsf.hsfbs.core.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface BaseDao<T> extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T> {


}
