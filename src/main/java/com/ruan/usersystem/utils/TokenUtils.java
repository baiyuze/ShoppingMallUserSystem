package com.ruan.usersystem.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenUtils {
    @Autowired
    private Map<String, Object> userInfo = new HashMap();
    private String secretString = "f0AmRwNStJF4M1EnWTMs/IfGBJ3gSIY04GSxA8me8IU=";
    // jti：jwt的唯一身份标识
    public static final String JWT_ID = UUID.randomUUID().toString();
    // 过期时间，单位毫秒
    public static final int EXPIRE_TIME = 60 * 60 * 1000; // 一个小时
//	public static final long EXPIRE_TIME = 7 * 24 * 3600 * 1000; // 一个星期

    // 由字符串生成加密key
    public SecretKey generalKey() {
        // 本地的密码解码
        SecretKey beas64Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
        return beas64Key;
    }

    /***
     * 加密生成jwt
     * @param name
     * @param account
     * @return
     */
    public String createJwtToken(String name, String account,int userId)  {
        Date date = new Date();
        long t = date.getTime() + 60 * 60 * 2 * 1000;
        Date exp = new Date(t);
        SecretKey key = generalKey();

        String jws = Jwts.builder()
//                .setExpiration(exp)
                .claim("name", name)
                .claim("exp", t)
                .claim("account", account)
                .claim("userId",userId)
                .signWith(key).compact();


        return jws;
    }
    public Map<String, Object> decryptJwt(String compactJws) {
        SecretKey key = generalKey();

        try {
            Claims claims =  Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(compactJws).getBody();
            String userId = claims.get("userId").toString();
            String account = claims.get("account").toString();
            userInfo.put("userId", userId);
            userInfo.put("account", account);
            return userInfo;
        } catch (JwtException e) {
            return null;
        }
    }


}