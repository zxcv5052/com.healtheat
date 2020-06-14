package com.healthteat.web.controller;

import com.healthteat.service.MemberService;
import com.healthteat.common.domain.TemplateResult;
import com.healthteat.web.dto.PageRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class IndexController {
    private final MemberService memberService;

    @GetMapping("/api/members")
    public TemplateResult getMembers(@RequestBody PageRequestDto requestDto){
        return memberService.findAllDesc(requestDto);
    }
}
