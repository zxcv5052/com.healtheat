package com.healthteat.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT p FROM Member p WHERE p.member_id = :member_id")
    Member findById(@Param("member_id") String member_id);
}
