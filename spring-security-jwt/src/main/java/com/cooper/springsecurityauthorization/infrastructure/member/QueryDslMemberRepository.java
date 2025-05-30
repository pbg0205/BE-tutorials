package com.cooper.springsecurityauthorization.infrastructure.member;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import com.cooper.springsecurityauthorization.domain.member.model.Member;
import com.cooper.springsecurityauthorization.domain.member.repository.MemberRepository;

@Repository
@RequiredArgsConstructor
public class QueryDslMemberRepository implements MemberRepository {

	private final JpaMemberRepository jpaMemberRepository;

	@Override
	public Member findByEmail(final String email) {
		return jpaMemberRepository.findByEmail(email);
	}
}
