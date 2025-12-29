package com.bank.performance.dao;


import com.bank.performance.core.base.BaseDao;
import com.bank.performance.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * @author yj
 * @date 2021-04-26 9:34
 */
public interface UserDao extends BaseDao<User> {

    Optional<User> findByPhoneAndPassword(String phone, String password);

    List<User> findByNameLikeAndPhoneLike(String name,String phone);

    Boolean existsByPhone(String phone);
}
