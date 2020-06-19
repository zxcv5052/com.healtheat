package com.healthteat.web.dto;

import com.healthteat.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

@Getter
public class MemberLoginRequestDto {
    private String member_id;
    private String member_pw;
    public MemberLoginRequestDto(Member entity){
        this.member_id = entity.getMember_id();
        this.member_pw = entity.getMember_pw();
    }
    @Builder
    public MemberLoginRequestDto(String member_id, String member_pw)
    {
        this.member_id = member_id;
        this.member_pw = member_pw;
    }
}
