package com.healthteat.web.controller;

import com.healthteat.service.MemberService;
import com.healthteat.common.domain.TemplateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class IndexController {
    private final MemberService memberService;

    @GetMapping("/api/members")
    public TemplateResult/*String*/ getMembers(Model model,
                                               @PageableDefault(
                                                            size = 5, page = 1, sort = "id", direction = Sort.Direction.DESC)
                                                            Pageable pageable){
//        if(sessionUser.getRole().getKey().equals("ADMIN")){
//            model.addAttribute("members",memberService.findAllDesc());
//            return "mustache";
//            return memberService.findAllDesc();
//        }
//        else{
//            return "mustache";
//        }
        return memberService.findAllDesc(pageable);
    }
}
