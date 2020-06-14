package com.healthteat.service;

import com.healthteat.common.domain.TemplateResult;
import com.healthteat.domain.member.Member;
import com.healthteat.domain.member.MemberRepository;
import com.healthteat.web.dto.*;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TemplateResult create(MemberSaveRequestDto requestDto){
        /*
        String pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{9,12}$";
        Matcher matcher = Pattern.compile(pwPattern).matcher(password);
         */
        requestDto.replace(passwordEncoder.encode(requestDto.getMember_pw()));
        Object object = memberRepository.save(requestDto.toEntity()).getId();
        if(object != null) return TemplateResult.OK();
        else return TemplateResult.ERROR("FAIL");
    }

    @Transactional(readOnly = true)
    public TemplateResult login(MemberLoginRequestDto requestDto){
       Member member = memberRepository.findById(requestDto.getMember_id());
       if(Objects.nonNull(member))
       {
           if(passwordEncoder.matches(requestDto.getMember_pw(), member.getMember_pw())){
               return TemplateResult.OK(member);
           }
           else
               return TemplateResult.ERROR("NOT CORRECT PASSWORD.");
       }
       else return TemplateResult.ERROR("NO CORRECT ID." + requestDto.getMember_id());
    }

    @Transactional
    public Long update(Long id, MemberUpdateRequestDto requestDto){
        return null;
    }

    @Transactional(readOnly = true)
    public TemplateResult findAllDesc(PageRequestDto requestDto){
        int size = Integer.parseInt(requestDto.getSize());
        int page = Integer.parseInt(requestDto.getPage());
        int fromIndex = (page-1) * size;
        int toIndex = fromIndex + size;
        int listSize = memberRepository.findAll().size();
        List<MemberResponseDto> list = memberRepository.findAll()
                .subList(fromIndex, toIndex >= listSize ? listSize : toIndex)
                .stream()
                .map(MemberResponseDto::new).collect(Collectors.toList());
        if(list.size() != 0){
            return TemplateResult.OK(list);
        }
        else{
            return TemplateResult.ERROR("ERROR");
        }
    }

    @Transactional
    public void delete(Long id){

    }

}
