package com.healthteat.web.controller;

import com.healthteat.service.MemberService;
import com.healthteat.common.domain.TemplateResult;
import com.healthteat.service.MemberServiceImpl;
import com.healthteat.web.dto.PageRequestDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = {"1. Index"})

@RequiredArgsConstructor
@RestController
public class IndexController {
    private final MemberService memberService;

    @GetMapping("/api/members")
    public TemplateResult getMembers(@RequestBody PageRequestDto requestDto) {
        return memberService.readAll(requestDto);
    }

}
