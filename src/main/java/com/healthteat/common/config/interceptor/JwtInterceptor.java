package com.healthteat.common.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.healthteat.common.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private static final String HEADER_AUTH = "Authorization";

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 가지고 있는 것은 AccessToken
        final String token = request.getHeader(HEADER_AUTH);
        log.info(token);
        // header 값 존재 && BlackList 체크.
        if (token != null && !jwtService.isUsable(token)) {
            return true;
        } else {
            return false;
        }
        // AccessToken 만료일
        if(jwtService.checkAccessTokenExp(token)){

        }
    }
}