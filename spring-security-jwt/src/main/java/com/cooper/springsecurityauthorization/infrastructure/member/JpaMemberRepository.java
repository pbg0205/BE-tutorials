package com.cooper.springsecurityauthorization.infrastructure.member;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooper.springsecurityauthorization.domain.member.model.Member;

public interface JpaMemberRepository extends JpaRepository<Member, Long> {
	Member findByEmail(String email);
}
