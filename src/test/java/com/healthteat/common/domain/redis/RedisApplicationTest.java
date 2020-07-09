package com.healthteat.common.domain.redis;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisApplicationTest {
    @Autowired
    private BlackListTokenRedisRepository blackListTokenRedisRepository;

    @Test
    public void 기본_등록_조회(){
        BlackListToken blackListToken = BlackListToken.builder().accessToken("black").delay(10000).build();
        blackListTokenRedisRepository.save(blackListToken);

        BlackListToken saved = blackListTokenRedisRepository.findById("black").get();
        Assertions.assertThat(saved.getAccessToken()).isEqualTo(blackListToken.getAccessToken());
        Assertions.assertThat(saved.getDelay()).isEqualTo(blackListToken.getDelay());

    }
}
