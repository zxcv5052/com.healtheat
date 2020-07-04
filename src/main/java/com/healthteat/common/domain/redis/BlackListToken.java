package com.healthteat.common.domain.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("blacklist")

@ToString
@Getter
@Setter
public class BlackListToken{

    @Id
    private String accessToken;

    @TimeToLive
    private long delay;

    @Builder
    public BlackListToken(String accessToken, long delay){
        this.accessToken = accessToken;
        this.delay = delay;
    }

}
