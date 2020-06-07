package com.healthteat.config.auth.dto;

import com.healthteat.domain.member.Member;
import com.healthteat.domain.member.Role;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private Role role;

    public SessionUser(Member member) {
        this.name = member.getMember_name();
        this.email = member.getMember_id();
        this.role = member.getRole();
    }
}
