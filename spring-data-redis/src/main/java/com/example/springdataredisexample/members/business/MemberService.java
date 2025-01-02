package com.example.springdataredisexample.members.business;

import com.example.springdataredisexample.members.domain.Member;
import com.example.springdataredisexample.members.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member createMember(String memberName) {
        return memberRepository.save(Member.of(memberName));
    }

    public Member getMember(String memberId) {
        return memberRepository.findById(memberId).orElseThrow(RuntimeException::new);
    }

}
