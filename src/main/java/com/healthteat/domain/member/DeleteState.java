package com.healthteat.domain.member;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeleteState {
    DELETE("삭제","DELETE"),
    REMAIN("존재","REMAIN");

    private final String key;
    private final String title;
}
