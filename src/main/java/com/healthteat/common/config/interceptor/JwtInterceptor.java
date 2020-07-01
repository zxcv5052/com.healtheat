package com.healthteat.common.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.healthteat.common.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor{
    private static final String HEADER_AUTH = "Authorization";

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 가지고 있는 것은 AccessToken
        final String token = request.getHeader(HEADER_AUTH);
        // AccessToken 만료일 확인
        boolean isExpire = jwtService.getExpToken(token);

        if(token != null && isExpire && jwtService.isUsable(token)){
            return true;
        }else{
            throw new Exception();
        }
    }

}