package com.healthteat.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PageRequestDto {
    private int page;
    private int size;

    @Builder
    public PageRequestDto(int page, int size)
    {
        this.page = page;
        this.size = size;
    }
}
