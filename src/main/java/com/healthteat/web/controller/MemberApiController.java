package com.healthteat.web.controller;

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

    @PostMapping
    public TemplateResult login(@RequestBody MemberLoginRequestDto requestDto){
       return memberService.login(requestDto);
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
