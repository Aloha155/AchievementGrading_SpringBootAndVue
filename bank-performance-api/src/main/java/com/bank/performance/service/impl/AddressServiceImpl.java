package com.bank.performance.service.impl;


import com.bank.performance.core.base.BaseServiceImpl;
import com.bank.performance.dao.AddressDao;
import com.bank.performance.entity.Address;
import com.bank.performance.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * @author yj
 * @date 2021-04-26 11:42
 */
@Service
public class AddressServiceImpl extends BaseServiceImpl<AddressDao, Address> implements AddressService {

    private Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);
}