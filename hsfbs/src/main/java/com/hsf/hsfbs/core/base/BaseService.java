package com.hsf.hsfbs.core.base;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.List;

public interface BaseService<T> {
    T save(T t);

    T get(Integer id);

    T update(T t);

    boolean delete(Integer id);

    List<T> listAll(Sort sort);

    List<T> listAll();

    Long count();

}
