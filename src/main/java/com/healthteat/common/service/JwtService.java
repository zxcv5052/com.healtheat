package com.healthteat.common.service;

import com.healthteat.common.domain.redis.BlackListToken;
import com.healthteat.common.domain.redis.BlackListTokenRedisRepository;
import com.healthteat.common.domain.redis.RefreshToken;
import com.healthteat.common.domain.redis.RefreshTokenRedisRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.AccessControlContext;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    private final int ACCESS_TOKEN = 60000*30; // 1 MINUTE
    private final int REFRESH_TOKEN = 60000*60*24*7; // 7 DAYS

    private static final String SALT =  "healtheatSecret";
    private final BlackListTokenRedisRepository blackListTokenRedisRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    public <T> String createToken(boolean checkToken, T data, String subject){
        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject)    // 토큰 제목
                .setAudience("client") // 토큰 대상자
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (checkToken? ACCESS_TOKEN : REFRESH_TOKEN)))
                .claim("member", data)
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();
        return jwt;
    }

    public boolean isUsable(String jwt) {
        return blackListTokenRedisRepository.findById(jwt).isPresent();
    }

    private byte[] generateKey(){
            byte[] key = null;
            try {
                key = SALT.getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException e) {
            }
            return key;
    }

    public boolean checkAccessTokenExp(String token) throws UnsupportedEncodingException {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(SALT.getBytes("UTF-8"))
                .parseClaimsJws(token);
        try{
            claims.getBody().getExpiration();
            return true;
        }catch (ExpiredJwtException e){
            return false;
        }
    }

    public RefreshToken getNewAccessToken(String token) throws UnsupportedEncodingException {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(SALT.getBytes("UTF-8"))
                .parseClaimsJws(token);

        @SuppressWarnings("unchecked")
        Map<String, Object> value = (LinkedHashMap<String, Object>) claims.getBody().get("comHealth");
        String payload = (String) value.get("id");
        RefreshToken refreshToken = refreshTokenRedisRepository.findByAccessToken(token).orElse(null);
        if(refreshToken == null){
            return null;
        }
        else{
            RefreshToken newRefresh = RefreshToken.builder()
                                                    .refreshToken(refreshToken.getRefreshToken())
                                                    .accessToken(createToken(false,payload,"comHealth"))
                                                    .build();
            refreshTokenRedisRepository.save(newRefresh);
            return newRefresh;
        }
    }

    public boolean createBlackList(String jwt){

        BlackListToken blackListToken = BlackListToken.builder()
                                        .accessToken(jwt)
                                        .delay(ACCESS_TOKEN)
                                        .build();

        RefreshToken refreshToken = refreshTokenRedisRepository.findByAccessToken(jwt).orElse(null);
        log.info(refreshToken.getRefreshToken()+"- refresh");
        log.info(blackListToken.getAccessToken()+"- black");

        if(refreshToken == null) return false;
        log.info(refreshToken.getRefreshToken()+"- refresh");
        log.info(blackListToken.getAccessToken()+"- black");
        blackListTokenRedisRepository.save(blackListToken);
        refreshTokenRedisRepository.delete(refreshToken);
        return true;
    }
}
