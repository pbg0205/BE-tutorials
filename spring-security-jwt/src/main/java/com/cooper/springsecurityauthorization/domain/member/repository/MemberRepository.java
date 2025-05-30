package com.cooper.springsecurityauthorization.domain.member.repository;

import com.cooper.springsecurityauthorization.domain.member.model.Member;

public interface MemberRepository {
	Member findByEmail(String email);
}
