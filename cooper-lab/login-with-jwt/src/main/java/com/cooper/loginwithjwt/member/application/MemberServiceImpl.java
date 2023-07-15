package com.cooper.loginwithjwt.member.application;

import com.cooper.loginwithjwt.common.error.ErrorType;
import com.cooper.loginwithjwt.member.domain.Member;
import com.cooper.loginwithjwt.member.domain.MemberRepository;
import com.cooper.loginwithjwt.member.dto.MemberCreateRequestDto;
import com.cooper.loginwithjwt.member.dto.MemberCreateResponseDto;
import com.cooper.loginwithjwt.member.exception.MemberEmailExistException;
import com.cooper.loginwithjwt.member.exception.message.MemberExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public MemberCreateResponseDto createMember(MemberCreateRequestDto memberCreateRequestDto) {
        String inputEmail = memberCreateRequestDto.getEmail();

        if (memberRepository.existsByEmail(inputEmail)) {
            throw new MemberEmailExistException(
                    MemberExceptionMessages.MEMBER_EMAIL_EXIST_MESSAGE, ErrorType.BAD_REQUEST_ERROR, inputEmail);
        }

        Member member = Member.userRole(
                inputEmail,
                memberCreateRequestDto.getName(),
                memberCreateRequestDto.getNickname(),
                passwordEncoder.encode(memberCreateRequestDto.getPassword()));

        Member savedMember = memberRepository.save(member);

        return new MemberCreateResponseDto(savedMember.getEmail(), savedMember.getName());
    }

}
