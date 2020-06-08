package com.healthteat.common.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemplateResult<T> {
    private Integer code;
    private String message;
    private T data;

    // only result OK
    public static <T> TemplateResult<T> OK(){
        return (TemplateResult<T>) TemplateResult.builder()
                .code(200)
                .message("SUCCESS")
                .build();
    }

    // result OK && data exist
    public static <T> TemplateResult<T> OK(T data){
        return (TemplateResult<T>) TemplateResult.builder()
                .code(200)
                .message("SUCCESS")
                .data(data)
                .build();
    }

    // result ERROR
    public static <T> TemplateResult<T> ERROR(String message){
        return (TemplateResult<T>) TemplateResult.builder()
                .code(400)
                .message(message)
                .build();
    }

}
