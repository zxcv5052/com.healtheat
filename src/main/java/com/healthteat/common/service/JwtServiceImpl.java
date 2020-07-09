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
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final int ACCESS_TOKEN = 60000*30; // 1 MINUTE
    private final int REFRESH_TOKEN = 60000*60*24*7; // 7 DAYS

    private static final String SALT =  "healtheatSecret";
    private final BlackListTokenRedisRepository blackListTokenRedisRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    @Override
    public <T> String createRefreshToken(T data, String subject){
        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                //.setHeaderParam("regDate", System.currentTimeMillis())
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN))
                .claim("comHealth", data)
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();

        return jwt;
    }
    @Override
    public <T> String createAccessToken(T data, String subject){
        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                //.setHeaderParam("regDate", System.currentTimeMillis())
                .setSubject(subject)
                .setIssuedAt(new Date())        // 같은 payload가 생기지 않게
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN))
                .claim("comHealth", data)
                .signWith(SignatureAlgorithm.HS256, this.generateKey())
                .compact();
        return jwt;
    }

    @Override
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

    @Override
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
    @Override
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
                                                    .accessToken(createAccessToken("comHealth",payload))
                                                    .build();
            refreshTokenRedisRepository.save(newRefresh);
            return newRefresh;
        }
    }

    @Override
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
