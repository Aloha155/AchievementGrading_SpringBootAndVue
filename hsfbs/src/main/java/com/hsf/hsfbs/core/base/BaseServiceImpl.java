package com.hsf.hsfbs.core.base;

import com.hsf.hsfbs.core.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.List;


public class BaseServiceImpl<M extends BaseDao<T>, T> implements BaseService<T> {

    @Autowired
    protected M baseDao;

    public BaseServiceImpl() {

    }

    @Override
    public T save(T t) {
        return baseDao.save(t);
    }

    @Override
    public T get(Integer id) {
        return baseDao.findById(id).orElseThrow(() -> new NotFoundException("没有记录"));
    }

    @Override
    public T update(T t) {
        return baseDao.save(t);
    }

    @Override
    public boolean delete(Integer id) {
        try {
            baseDao.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<T> listAll(Sort sort) {
        return baseDao.findAll(sort);
    }

    @Override
    public List<T> listAll() {
        return baseDao.findAll();
    }

    @Override
    public Long count() {
        return baseDao.count();
    }


}
