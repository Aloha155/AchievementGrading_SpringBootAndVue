package com.hsf.hsfbs.core.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


/**
 * @author yj
 * @date 2021-04-26 15:44
 */
public class Md5Util {

    private Logger logger = LoggerFactory.getLogger(Md5Util.class);

    private static final String KEY = "hsf";

    private static final String PUBLIC_KEY;

    static {
        PUBLIC_KEY = Base64.getEncoder().encodeToString(KEY.getBytes(StandardCharsets.UTF_8));
    }

    public static String Md5encryption(String password, String base64Security) {
        String value = password + PUBLIC_KEY + base64Security;
        return DigestUtils.md5DigestAsHex(value.getBytes(StandardCharsets.UTF_8));
    }

}