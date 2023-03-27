package com.cooper.springweb.member.domain;

import java.util.Optional;

public interface MemberRepository {

	Member save(Member member);

	Optional<Member> findById(String userId);

}
