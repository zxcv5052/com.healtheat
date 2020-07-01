package com.healthteat.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@RequiredArgsConstructor
@Configuration
@EnableRedisRepositories
public class RedisConfig {
    private final RedisProperties redisProperties;

    //  Use lettuce
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        /**
         * 그럼 아래와 같은 @class 데이터가 붙어서 Serializer를 통해 객체로 변경해줄 때 어떤 VO로 매핑할 지 체크한다.
         * {"@class":"com.example.demo.common.redis.RedisVO","key1":"value1","key2":"value2"}
         *
         *  ※ 표준화 된 하나의 VO만 사용하면 해당 코드를 아래와 같이 변경
         *  redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(RedisVO.class));
         */
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}