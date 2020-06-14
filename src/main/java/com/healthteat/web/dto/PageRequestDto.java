package com.healthteat.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PageRequestDto {
    private String page;
    private String size;

    @Builder
    public PageRequestDto(String page, String size)
    {
        this.page = page;
        this.size = size;
    }
}
