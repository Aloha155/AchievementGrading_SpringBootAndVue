package com.hsf.hsfbs.service.impl;


import com.hsf.hsfbs.core.base.BaseServiceImpl;
import com.hsf.hsfbs.core.exception.NotFoundException;
import com.hsf.hsfbs.core.exception.ProgramException;
import com.hsf.hsfbs.core.util.RegexUtil;
import com.hsf.hsfbs.entity.dao.UserDao;
import com.hsf.hsfbs.entity.entity.User;
import com.hsf.hsfbs.service.service.UserService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


/**
 * @author yj
 * @date 2021-04-26 11:39
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao, User> implements UserService {


    @Override
    public User login(String phone, String password) {
        return baseDao.findByPhoneAndPassword(phone, password).orElseThrow(() -> new NotFoundException("登陆失败，请检查用户名密码"));
    }

    @Override
    public User register(User user) {
        if (baseDao.existsByPhone(user.getPhone())) {
            throw new ProgramException("当前账户已经存在无需注册");
        }
        return baseDao.save(user);
    }

    @Override
    public List<User> listByLikeUsernameAndPhone(String name, String phone) {
        Specification<User> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (RegexUtil.checkObjectIsNotNull(phone)) {
                predicates.add(criteriaBuilder.like(root.get("phone"), "%" + phone + "%"));
            }
            if (RegexUtil.checkObjectIsNotNull(name)) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };;
        return baseDao.findAll(specification);
    }
}
//