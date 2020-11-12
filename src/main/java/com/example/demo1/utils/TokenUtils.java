package com.example.demo1.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;




public class TokenUtils {

//    String exp =
    public String createJwtToken(String name, String account)  {
        Date date = new Date();
        long t = date.getTime() + 60 * 60 * 2;
        Date exp = new Date(t);
//        JSONObject payload = new JSONObject();
//        JSONObject header = new JSONObject();
//        payload.put("name", name);
//        payload.put("account", account);
//        payload.put("exp", exp);
//        header.put("alg","HS256");
//        header.put("typ","JWT");

        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String jws = Jwts.builder()
                .setExpiration(exp)
                .claim("name", name)
                .claim("account", account)
                .signWith(key).compact();
        System.out.println(jws);

        return jws;
    }
}