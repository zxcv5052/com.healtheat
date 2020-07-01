package com.healthteat.common.service;

import java.util.Map;

public interface JwtService {
    // refresh 토큰 생성
    <T> String createRefreshToken(String key, T data, String subject);

    // access 토큰 생성
    <T> String createAccessToken(String key, T data, String subject);

    // refresh 토큰이 있다면 access토큰 생성
    public <T> String reCreateAccessToken(String member_name);

    // key로 get
    Map<String, Object> get(String key);

    //
    int getMemberId();

    // 만료일 확인
    public boolean getExpToken(String jwt);

    // 생성가능여부
    boolean isUsable(String jwt);

}