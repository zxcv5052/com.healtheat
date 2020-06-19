package com.healthteat.web.dto;

import com.healthteat.domain.member.Member;
import lombok.Getter;

@Getter

public class MemberLoginResponseDto {
    private Long id;
    private String member_id;
    private String member_name;

    public MemberLoginResponseDto(Member entity){
        this.id = entity.getId();
        this.member_id = entity.getMember_id();
        this.member_name = entity.getMember_name();
    }
}
