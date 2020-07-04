package com.healthteat.common.domain.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListTokenRedisRepository extends CrudRepository<BlackListToken, String> {

}
