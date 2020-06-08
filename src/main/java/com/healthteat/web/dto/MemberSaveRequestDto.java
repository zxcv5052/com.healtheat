package com.healthteat.web.dto;

import com.healthteat.domain.member.DeleteState;
import com.healthteat.domain.member.Member;
import com.healthteat.domain.member.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class MemberSaveRequestDto {

    private String member_id;
    private String member_name;
    private String member_pw;
    private DeleteState delete_state = DeleteState.REMAIN;

    @Builder
    public MemberSaveRequestDto(String member_id, String member_pw, String member_name)
    {
        this.member_id = member_id;
        this.member_pw = member_pw;
        this.member_name = member_name;
    }

    public Member toEntity()
    {
        return Member.builder()
                .member_id(member_id)
                .member_pw(member_pw)
                .member_name(member_name)
                .delete_state(delete_state)
                .role(Role.USER)
                .build();
    }
    public void replace(String encode_password)
    {
        this.member_pw = encode_password;
    }
}
