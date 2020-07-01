package com.healthteat.common.service;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class JwtServiceImpl implements JwtService {
    private final int ACCESS_TOKEN = 60000; // 1 MINUTE
    private final int REFRESH_TOKEN = 60000*60*24*7; // 7 DAYS

    private static final String SALT =  "healtheatSecret";


    @Override
    public <T> String createRefreshToken(String key, T data, String subject){
        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                //.setHeaderParam("regDate", System.currentTimeMillis())
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN))
                .claim(key, data)
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();

        return jwt;
    }
    @Override
    public <T> String createAccessToken(String key, T data, String subject){
        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                //.setHeaderParam("regDate", System.currentTimeMillis())
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN))
                .claim(key, data)
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();
        return jwt;
    }

    @Override
    public <T> String reCreateAccessToken(String member_name){
        return null;
    }

    @Override
    public boolean isUsable(String jwt) {
        try{
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(this.generateKey())
                    .parseClaimsJws(jwt);
            return true;

        }catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    private byte[] generateKey(){
            byte[] key = null;
            try {
                key = SALT.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
            }
            return key;
        }
    @Override
    public Map<String, Object> get(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwt = request.getHeader("Authorization");
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SALT.getBytes("UTF-8"))
                    .parseClaimsJws(jwt);
        } catch (Exception e) {

        }
        Map<String, Object> value = (LinkedHashMap<String, Object>)claims.getBody().get(key);
        return value;
    }
    @Override
    public boolean getExpToken(String jwt){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(SALT).parseClaimsJws(jwt);

            Date exp = claims.getBody().getExpiration();

            Date now = new Date();

            if(exp.after(now)){
                return true;
            }
            return false;
        }catch (ExpiredJwtException e){
            return false;
        }
    }
    @Override
    public int getMemberId()
    {
        return (int)this.get("member").get("memberId");
    }
}
