package com.healthteat.common.domain.redis;

import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<BlackListToken, String> {

}
