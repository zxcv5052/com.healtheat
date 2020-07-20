package com.healthteat.common.config.filter;

import com.healthteat.common.config.CustomUserDetailsService;
import com.healthteat.domain.member.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     *
     * 1. CustomAuthenticationProvider에서 UserDetailsService를 통해 조회한 정보와 입력받은 비밀번호가 일치하는지 확인
     * 2. 일치하는 경우 인증된 토큰을 생성하여 반환해주어야 한다.
     * 3. DB에 저장된 사용자 비밀번호는 암호화가 되어 있기 때문에, 입력으로부터 들어온 비밀번호를 PasswordEncoder를 통해 암호화하여 DB에서 조회한 사용자의 비밀번호와 매칭되는지 확인
     * 4. 비밀번호 매칭되지 않는 경우 BadCredentialsException 발생시켜 처리
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String member_id = token.getName();
        String member_pwd = (String) token.getCredentials();

        MemberDetails member = (MemberDetails) customUserDetailsService.loadUserByUsername(member_id);

        if(!passwordEncoder.matches(member_pwd, member.getPassword())){
            return null;
        }
        return new UsernamePasswordAuthenticationToken(member, member_pwd, member.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
