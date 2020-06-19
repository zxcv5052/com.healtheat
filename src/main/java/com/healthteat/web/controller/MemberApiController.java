package com.healthteat.web.controller;

import com.healthteat.common.service.JwtService;
import com.healthteat.service.MemberService;
import com.healthteat.web.dto.MemberLoginRequestDto;
import com.healthteat.web.dto.MemberLoginResponseDto;
import com.healthteat.web.dto.MemberSaveRequestDto;
import com.healthteat.common.domain.TemplateResult;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Api(tags = {"2. Member"})

@RequiredArgsConstructor
@MemberApiControllerAnnotation
public class MemberApiController {

    private final MemberService memberService;
    private final JwtService jwtService;

    @PostMapping
    public TemplateResult login(@RequestBody MemberLoginRequestDto requestDto, HttpServletResponse response){
        TemplateResult result = memberService.login(requestDto);
        if(result.getCode() == 200) {
            String token = jwtService.create("member",
                                            (MemberLoginResponseDto)result.getData(),
                                            "user");
            response.setHeader("Authorization", token);
        }
        result = TemplateResult.OK();
        return result;
    }

    @PostMapping("/register")
    public TemplateResult create(@RequestBody MemberSaveRequestDto requestDto){
        return memberService.create(requestDto);
    }
    @DeleteMapping
    public TemplateResult logout(@RequestBody String member_id){
        return null;
    }
}
