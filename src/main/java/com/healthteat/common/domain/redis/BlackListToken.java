package com.healthteat.common.domain.redis;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("blacklist")

@ToString
@Getter
@Setter
public class BlackListToken{

    @Id
    private String accessToken;

}
