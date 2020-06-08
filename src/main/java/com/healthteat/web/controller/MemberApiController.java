package com.healthteat.web.controller;

import com.healthteat.common.service.JwtServiceImpl;
import com.healthteat.service.MemberService;
import com.healthteat.web.dto.MemberLoginRequestDto;
import com.healthteat.web.dto.MemberSaveRequestDto;
import com.healthteat.common.domain.TemplateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor

@MemberApiControllerAnnotation
public class MemberApiController {

    private final MemberService memberService;
    private final JwtServiceImpl jwtService;

    @PostMapping
    public TemplateResult login(@RequestBody MemberLoginRequestDto requestDto){
       return memberService.login(requestDto);
    }

    @PostMapping("/register")
    public TemplateResult create(@RequestBody MemberSaveRequestDto requestDto){
        return memberService.create(requestDto);
    }

    @GetMapping("/logout/{member_id}")
    public TemplateResult logout(@PathVariable String member_id){
        return null;
    }

}
