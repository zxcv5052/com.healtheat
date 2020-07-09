package com.healthteat.common.service;

import com.healthteat.common.domain.TemplateResult;
import com.healthteat.common.domain.redis.RefreshToken;

import java.io.UnsupportedEncodingException;


public interface JwtService {
    // refresh 토큰 생성
    <T> String createRefreshToken(T data, String subject);

    // access 토큰 생성
    <T> String createAccessToken(T data, String subject);

    boolean checkAccessTokenExp(String token) throws UnsupportedEncodingException;

    // Access Token 확인 ( Access Token Exist && Token Expired )
    RefreshToken getNewAccessToken(String jwt) throws UnsupportedEncodingException;

    // redis 확인
    boolean isUsable(String jwt);

    // 로그 아웃할 때 blacklist에 생성
    boolean createBlackList(String jwt);
}