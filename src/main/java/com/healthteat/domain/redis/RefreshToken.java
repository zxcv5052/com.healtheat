package com.healthteat.domain.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@ToString
@Getter
@RedisHash("refreshToken")
public class RefreshToken implements Serializable {

    @Id
    private String id; // member_id

    private String token;
    private Date expired;

    @Builder
    public RefreshToken(String id, String token, Date expired) {
        this.id = id;
        this.token = token;
        this.expired = expired;
    }
}
