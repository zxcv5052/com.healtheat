package com.healthteat.domain.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;

@ToString
@Getter
@RedisHash("blackListToken")
public class BlackListToken implements Serializable {

    @Id
    String accessToken;

    @Builder
    public BlackListToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
