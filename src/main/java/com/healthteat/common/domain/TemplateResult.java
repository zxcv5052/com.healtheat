package com.healthteat.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemplateResult<T> {
    public static final String SUCCESS_MESSAGE = "SUCCESS";
    public static final String SERVER_ERROR_MESSAGE = "FAIL";

    private Integer code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    // only result OK
    public static <T> TemplateResult<T> OK(){
        return (TemplateResult<T>) TemplateResult.builder()
                .code(200)
                .message(SUCCESS_MESSAGE)
                .build();
    }

    // result OK && data exist && Accept 가변인자 생각해보자.
    public static <T> TemplateResult<T> OK(T/*...*/ data){
        return (TemplateResult<T>) TemplateResult.builder()
                .code(200)
                .message(SUCCESS_MESSAGE)
                .data(data)
                .build();
    }

    // result ERROR
    public static <T> TemplateResult<T> ERROR(String message){
        return (TemplateResult<T>) TemplateResult.builder()
                .code(400)
                .message(SERVER_ERROR_MESSAGE)
                .build();
    }
    // result ERROR
    public static <T> TemplateResult<T> ERROR(int code, String message){
        return (TemplateResult<T>) TemplateResult.builder()
                .code(code)
                .message(SERVER_ERROR_MESSAGE)
                .build();
    }

}
