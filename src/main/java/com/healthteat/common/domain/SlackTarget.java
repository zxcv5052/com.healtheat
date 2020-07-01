package com.healthteat.common.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SlackTarget {
    CH_BOT("https://hooks.slack.com/services/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",
            "test");
    private final String webHookUrl;
    private final String channel;
}
