package com.healthteat.web.dto;

import com.healthteat.domain.member.DeleteState;
import com.healthteat.domain.member.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberListResponseDto {
    private Long id;
    private String member_id;
    private String member_name;
    private DeleteState delete_status;
    private LocalDateTime create_at;
    private LocalDateTime modified_at;

    public MemberListResponseDto(Member entity){
        this.id = entity.getId();
        this.member_id = entity.getMember_id();
        this.member_name = entity.getMember_name();
        this.delete_status = entity.getDelete_state();
        this.create_at = entity.getCreated_at();
        this.modified_at = entity.getModified_at();
    }

}
