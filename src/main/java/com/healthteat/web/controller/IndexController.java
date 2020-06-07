package com.healthteat.web.controller;

import com.healthteat.config.auth.LoginUser;
import com.healthteat.config.auth.dto.SessionUser;
import com.healthteat.domain.member.Member;
import com.healthteat.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class IndexController {
    private final MemberService memberService;
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("user",  user);

        // LOGIN ADMIN

        if(user!= null && user.getRole().getKey().equals("ROLE_ADMIN")){
            model.addAttribute("userName", user.getName());
            return "admin-index";
        }
        // NO LOGIN < USER OR ADMIN >
        else
        {
            if(user != null) model.addAttribute("user", user);
            return "user-index";
        }
    }

    @GetMapping("/api/members")
    public String getMembers(Model model, @LoginUser SessionUser user){
        return "admin-page";
    }

    @GetMapping("/api/members/{page}/{size}")
    public List<Member>/*String*/ getMembers(Model model, @LoginUser SessionUser sessionUser, @PathVariable String page, @PathVariable String size){
//        if(sessionUser.getRole().getKey().equals("ADMIN")){
//            model.addAttribute("members",memberService.findAllDesc());
//            return "mustache";
//            return memberService.findAllDesc();
//        }
//        else{
//            return "mustache";
//        }
        model.addAttribute("members",memberService.findAllDesc());
        return memberService.findAllDesc();
    }
}
