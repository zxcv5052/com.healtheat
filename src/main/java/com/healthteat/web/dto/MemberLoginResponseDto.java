package com.healthteat.web.dto;

import com.healthteat.domain.member.Member;
import lombok.Getter;

@Getter
public class MemberLoginResponseDto {

    private Long id;
    private String member_id;
    private String member_name;
    private String refresh_token;
    private String access_token;

    public MemberLoginResponseDto(Member entity,String refresh_token, String access_token){
        this.id = entity.getId();
        this.member_id = entity.getMember_id();
        this.member_name = entity.getMember_name();
        this.refresh_token = refresh_token;
        this.access_token = access_token;
    }
}
