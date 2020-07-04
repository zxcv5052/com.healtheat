package com.healthteat.common.service;

import com.healthteat.common.domain.TemplateResult;

import java.io.UnsupportedEncodingException;


public interface JwtService {
    // refresh 토큰 생성
    <T> String createRefreshToken(String key, T data, String subject);

    // access 토큰 생성
    <T> String createAccessToken(String key, T data, String subject);

    // Access Token 확인 ( Access Token Exist && Token Expired )
    boolean checkAccessTokenExp(String jwt) throws UnsupportedEncodingException;

    // redis 확인
    boolean isUsable(String jwt);

}