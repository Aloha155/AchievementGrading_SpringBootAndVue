package com.bank.performance.core.config;


import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


/**
 * @author yj
 * @date 2021-04-26 14:17
 */
@Data
@Component
@ConfigurationProperties("account")
public class AccountConfig {

    private String accessTokenSecretKey;

    private long tokenMaxValidTime;


    public String getAccessTokenSecretKey() {
        return Base64.getEncoder().encodeToString(accessTokenSecretKey.getBytes(StandardCharsets.UTF_8));
    }
}