package com.cooper.springsecurityauthorization.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooper.springsecurityauthorization.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByEmail(String email);
}
