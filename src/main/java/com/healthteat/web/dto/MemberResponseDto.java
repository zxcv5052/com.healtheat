package com.healthteat.web.dto;

import com.healthteat.domain.member.Member;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Getter
public class MemberResponseDto{
    private CommonResponseDto commonResponseDto;
    private Long id;
    private String member_id;
    private String member_name;
    private String delete_status;
    private LocalDateTime create_at;
    private LocalDateTime modified_at;

    public MemberResponseDto(CommonResponseDto commonResponseDto, Member entity){
        this.commonResponseDto = commonResponseDto;
        this.id = entity.getId();
        this.member_id = entity.getMember_id();
        this.member_name = entity.getMember_name();
        this.delete_status = entity.getDelete_state();
        this.create_at = entity.getCreated_at();
        this.modified_at = entity.getModified_at();
    }

    public MemberResponseDto(CommonResponseDto commonResponseDto) {
        this.commonResponseDto = commonResponseDto;
    }
}
