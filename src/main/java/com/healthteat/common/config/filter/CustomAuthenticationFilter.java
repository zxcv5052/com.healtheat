package com.healthteat.common.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthteat.web.dto.MemberLoginRequestDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest = null;
        try {
            MemberLoginRequestDto loginRequest = new ObjectMapper().readValue(request.getInputStream(), MemberLoginRequestDto.class);
            authRequest = new UsernamePasswordAuthenticationToken(loginRequest.getMember_id(), loginRequest.getMember_pw());
        } catch (IOException exception) {
            // 사용자가 입력한 값의 문제로 인하여 예외 발생 가능
        }
        setDetails(request, authRequest);
        // AbstractAuthenticationProcessingFilter의 AuthenticationManager
        return this.getAuthenticationManager().authenticate(authRequest);
    }


}
