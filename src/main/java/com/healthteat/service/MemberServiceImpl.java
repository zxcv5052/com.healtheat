package com.healthteat.service;

import com.healthteat.common.domain.TemplateResult;
import com.healthteat.common.service.JwtService;
import com.healthteat.domain.member.Member;
import com.healthteat.domain.member.MemberRepository;
import com.healthteat.web.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{

    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public TemplateResult create(@Valid MemberSaveRequestDto requestDto){

        Member member = memberRepository.findById(requestDto.getMember_id());

        requestDto.replace(passwordEncoder.encode(requestDto.getMember_pw()));
        Object object = memberRepository.save(requestDto.toEntity()).getId();
        if(object != null) return TemplateResult.OK();
        else return TemplateResult.ERROR("FAIL");
    }

    @Override
    @Transactional(readOnly = true)
    public TemplateResult login(MemberLoginRequestDto requestDto){

        Member member = memberRepository.findById(requestDto.getMember_id());
        String key = member.getId()+member.getMember_id();
        String payload = member.getId() +"";
        if(Objects.nonNull(member)
                && passwordEncoder.matches(requestDto.getMember_pw(), member.getMember_pw())) {
            String refreshToken = jwtService.createRefreshToken(key,
                    payload,
                    "user");
            String accessToken = jwtService.createAccessToken(key,
                    payload,
                    "user");
            return TemplateResult.OK(new MemberLoginResponseDto(member,refreshToken,accessToken));
        } else {
            return TemplateResult.ERROR("Check Id And Password");
        }
    }

    @Override
    @Transactional
    public TemplateResult update(MemberUpdateRequestDto requestDto){
        return null;
    }

    @Override
    @Transactional
    public TemplateResult delete(Long id){
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public TemplateResult readAll(PageRequestDto requestDto){
        int size = requestDto.getSize();
        int page = requestDto.getPage();
        int fromIndex = (page-1) * size;
        int toIndex = fromIndex + size;
        int listSize = memberRepository.findAll().size();
        List<MemberListResponseDto> list = memberRepository.findAll()
                .subList(fromIndex, toIndex >= listSize ? listSize : toIndex)
                .stream()
                .map(MemberListResponseDto::new).collect(Collectors.toList());
        if(list.size() != 0){
            return TemplateResult.OK(list);
        } else{
            return TemplateResult.ERROR("ERROR");
        }
    }


}
