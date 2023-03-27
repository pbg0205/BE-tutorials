package com.cooper.springweb.member.persistence;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.cooper.springweb.member.domain.Member;
import com.cooper.springweb.member.domain.MemberRepository;

@Repository
public class SimpleMemberRepository implements MemberRepository {

	private Map<String, Member> memberMap = new ConcurrentHashMap<>();

	@Override
	public Member save(Member member) {
		memberMap.put(member.getUserId(), member);
		return memberMap.get(member.getUserId());
	}

	@Override
	public Optional<Member> findById(String userId) {
		Member findMember = memberMap.get(userId);
		return Optional.ofNullable(findMember);
	}

}
