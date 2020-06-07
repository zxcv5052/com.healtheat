package com.healthteat.domain.member;

import com.healthteat.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor

@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String member_id;

    @Column(nullable = false)
    private String member_pw;

    @Column(nullable = false)
    private String member_name;

    @Column(nullable = false)
    private String delete_state;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String member_id, String member_pw, String member_name,String delete_state, Role role){
        this.member_id = member_id;
        this.member_name = member_name;
        this.member_pw = member_pw;
        this.delete_state = delete_state;
        this.role = role;
    }

    public Member update(String member_name){
        this.member_name = member_name;
        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
