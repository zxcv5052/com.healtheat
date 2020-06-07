package com.healthteat.web.dto;

import lombok.Getter;

@Getter
public class CommonResponseDto {
    private Integer code;
    private String message;

    public CommonResponseDto(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
