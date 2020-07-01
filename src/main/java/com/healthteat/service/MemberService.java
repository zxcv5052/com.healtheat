package com.healthteat.service;

import com.healthteat.common.domain.TemplateResult;
import com.healthteat.web.dto.MemberLoginRequestDto;
import com.healthteat.web.dto.MemberSaveRequestDto;
import com.healthteat.web.dto.MemberUpdateRequestDto;
import com.healthteat.web.dto.PageRequestDto;
import org.springframework.stereotype.Service;

public interface MemberService {

    TemplateResult create(MemberSaveRequestDto requestDto);

    TemplateResult login/*read*/(MemberLoginRequestDto requestDto);

    TemplateResult update(MemberUpdateRequestDto requestDto);

    TemplateResult delete(Long id);

    TemplateResult readAll(PageRequestDto requestDto);

}

