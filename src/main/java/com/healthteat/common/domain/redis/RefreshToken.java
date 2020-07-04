package com.healthteat.common.domain.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@ToString
@Getter
@NoArgsConstructor

@RedisHash("refreshToken")
public class RefreshToken implements Serializable {

    @Id
    private String refreshToken;

    private String accessToken;

    @TimeToLive(unit = TimeUnit.DAYS)
    public long getTimeToLive(){
        return 7;
    }

    @Builder
    public RefreshToken(String refreshToken, String accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }
}
