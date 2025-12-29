package com.bank.performance.service;


import com.bank.performance.core.base.BaseDao;
import com.bank.performance.core.base.BaseService;
import com.bank.performance.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author yj
 * @date 2021-04-26 9:34
 */
public interface UserService extends BaseService<User> {

    User login(String phone,String password);

    User register(User user);

    List<User> listByLikeUsernameAndPhone(String name, String phone);

}
