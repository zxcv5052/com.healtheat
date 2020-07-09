package com.healthteat.common.domain.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
        Optional<RefreshToken> findByAccessToken(@Param("accessToken") String accessToken);
}
