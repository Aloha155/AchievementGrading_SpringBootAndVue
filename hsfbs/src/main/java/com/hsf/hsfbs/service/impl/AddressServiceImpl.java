package com.hsf.hsfbs.service.impl;


import com.hsf.hsfbs.core.base.BaseServiceImpl;
import com.hsf.hsfbs.entity.dao.AddressDao;
import com.hsf.hsfbs.entity.entity.Address;
import com.hsf.hsfbs.service.service.AddressService;
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