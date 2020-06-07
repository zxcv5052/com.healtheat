package com.healthteat.web.controller;


import com.healthteat.config.auth.LoginUser;
import com.healthteat.config.auth.dto.SessionUser;
import com.healthteat.domain.member.Member;
import com.healthteat.service.MemberService;
import com.healthteat.web.dto.MemberLoginRequestDto;
import com.healthteat.web.dto.MemberResponseDto;
import com.healthteat.web.dto.MemberSaveRequestDto;
import com.healthteat.web.dto.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@MemberApiControllerAnnotation
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping
    public Object login(@RequestBody MemberLoginRequestDto requestDto){
        return memberService.login(requestDto);
    }

    @PostMapping("/register")
    public CommonResponseDto save(@RequestBody MemberSaveRequestDto requestDto){
        return memberService.save(requestDto);
    }

    @GetMapping("/logout/{member_id}")
    public String logout(@PathVariable String member_id){
        return "true";
    }

}
