package com.healthteat.service;

import com.healthteat.domain.member.Member;
import com.healthteat.domain.member.MemberRepository;
import com.healthteat.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public CommonResponseDto save(MemberSaveRequestDto requestDto){
        Object object = memberRepository.save(requestDto.toEntity()).getId();
        if(object != null) return new CommonResponseDto(200, "SUCCESS");
        else return new CommonResponseDto(400, "FAIL");
    }

    @Transactional
    public Object login(MemberLoginRequestDto memberLoginRequestDto){
       Member member = memberRepository.findById(memberLoginRequestDto.getMember_id());
       if(member != null)
       {
           if(member.getMember_pw().equals(memberLoginRequestDto.getMember_pw())){
               return new MemberResponseDto(new CommonResponseDto(200,"SUCCESS LOGIN"),member);
           }
           else
               return new MemberResponseDto(new CommonResponseDto(400,"NOT CORRECT PASSWORD."),member);
       }
       else return new CommonResponseDto(400,"NO CORRECT ID." + memberLoginRequestDto.getMember_id());
    }

    @Transactional
    public Long update(Long id, MemberUpdateRequestDto requestDto){
        return null;
    }

    @Transactional
    public List<Member> findAllDesc(){
        return new ArrayList<>(memberRepository.findAll());
    }

    @Transactional
    public void delete(Long id){

    }
}
