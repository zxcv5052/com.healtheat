package com.healthteat.common.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.healthteat.common.domain.redis.RefreshToken;
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

            // check access token Expiration
            boolean flag = jwtService.checkAccessTokenExp(token);

            // ok expiration
            if(flag) {
                return true;
            }
            else {
                RefreshToken newRefreshToken = jwtService.getNewAccessToken(token);
                // ok expiration _ but no exist refresh token at redis
                if(newRefreshToken == null){
                    return false;
                }
                else {
                    response.setHeader(HEADER_AUTH, newRefreshToken.getAccessToken());
                    return true;
                }
            }
        }
        // BlackList 존재 || header Empty
        else {
            log.info("helllooooo");
            return false;
        }

    }
}