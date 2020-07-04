package com.healthteat.common.service;

import com.healthteat.common.domain.TemplateResult;
import com.healthteat.common.domain.redis.BlackListTokenRedisRepository;
import com.healthteat.common.domain.redis.RefreshToken;
import com.healthteat.common.domain.redis.RefreshTokenRedisRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final int ACCESS_TOKEN = 60000*30; // 1 MINUTE
    private final int REFRESH_TOKEN = 60000*60*24*7; // 7 DAYS

    private static final String SALT =  "healtheatSecret";
    private final BlackListTokenRedisRepository blackListTokenRedisRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    @Override
    public <T> String createRefreshToken(String key, T data, String subject){
        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                //.setHeaderParam("regDate", System.currentTimeMillis())
                .setSubject(subject)
                .setIssuedAt(new Date())
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
                .setIssuedAt(new Date())        // 같은 payload가 생기지 않게
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN))
                .claim(key, data)
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
        }
        // 토큰 만료
        catch (ExpiredJwtException exception) {
            RefreshToken refreshToken = refreshTokenRedisRepository.findByAccessToken(token).orElse(null);
            if(refreshToken == null){
                return false;
            }
            else{
                RefreshToken newRefresh = RefreshToken.builder()
                                                        .refreshToken(refreshToken.getRefreshToken())
                                                        .accessToken(createAccessToken())
                                                        .build();
                refreshTokenRedisRepository.save(newRefresh);
                return true;
            }
        }
    }
}
