package com.healthteat.domain.redis;

import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<TokenRedisRepository, String> {
}
