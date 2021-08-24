package com.hsf.hsfbs.core.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 安全工具
 * @author yj
 * @date 2021-04-26 16:14
 */
public class SafetyUtil {

    private Logger logger = LoggerFactory.getLogger(SafetyUtil.class);


    public static String phoneSafety(String phone){
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
    }

    public static String idCardSafety(String idCard){
        return idCard.replaceAll("(\\d{4})\\d{10}(\\w{4})","$1*****$2");
    }

    public static String emailSafety(String email){
        return email.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4");
    }

}