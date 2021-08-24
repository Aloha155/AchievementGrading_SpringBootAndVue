package com.hsf.hsfbs.core.util;


import io.jsonwebtoken.*;
import lombok.Data;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.UUID;


/**
 * @author yj
 * @date 2021-04-26 14:09
 */
public class JwtUtil {

    private Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    public static Claims parseJWT(String jsonWebToken, String base64Security) {
        try {
            return Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken).getBody();
        } catch (Exception e) {
            throw e;
        }
    }

    public static String createJWT(Integer userId ,Long nowMillis,Long expiresSecond ,String base64Security ) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date now = new Date(nowMillis);
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        SecretKeySpec signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuer("hsf")
                .setHeaderParam("typ", "JWT")
                .compressWith(CompressionCodecs.GZIP)
                .claim("userId", userId)
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        if (expiresSecond >= 0) {
            long expMillis = nowMillis + expiresSecond;
            Date exp =new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }
        //生成JWT
        return builder.compact();
    }
}