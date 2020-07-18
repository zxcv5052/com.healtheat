package com.healthteat.web.controller;

import com.healthteat.service.MemberService;
import com.healthteat.web.dto.MemberLoginRequestDto;
import com.healthteat.web.dto.MemberLoginResponseDto;
import com.healthteat.web.dto.MemberSaveRequestDto;
import com.healthteat.common.domain.TemplateResult;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = {"2. Member"})

@Slf4j
@RequiredArgsConstructor
@MemberApiControllerAnnotation
public class MemberApiController {
    private final String HEADER_AUTH = "Authorization";
    private final MemberService memberService;

    @PostMapping
    public TemplateResult login(@RequestBody MemberLoginRequestDto requestDto, HttpServletResponse response){
        TemplateResult result = memberService.login(requestDto);
        if(result.getCode() == 200) {
            MemberLoginResponseDto responseDto = (MemberLoginResponseDto) result.getData();
            response.setHeader(HEADER_AUTH, responseDto.getAccess_token());
        }
        return result;
    }

    @PostMapping("/register")
    public TemplateResult create(@RequestBody MemberSaveRequestDto requestDto){
        return memberService.create(requestDto);
    }
    @DeleteMapping
    public TemplateResult logout(HttpServletRequest request){
        String token = request.getHeader(HEADER_AUTH);
        return memberService.delete(token);
    }
}
