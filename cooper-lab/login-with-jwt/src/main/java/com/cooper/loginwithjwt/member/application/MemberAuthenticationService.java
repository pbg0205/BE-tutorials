package com.cooper.loginwithjwt.member.application;

import com.cooper.loginwithjwt.common.error.ErrorType;
import com.cooper.loginwithjwt.member.domain.Member;
import com.cooper.loginwithjwt.member.domain.MemberRepository;
import com.cooper.loginwithjwt.member.dto.MemberAuthenticationDto;
import com.cooper.loginwithjwt.member.exception.MemberEmailNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.cooper.loginwithjwt.member.exception.message.MemberExceptionMessages.MEMBER_EMAIL_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class MemberAuthenticationService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(
                () -> new MemberEmailNotFoundException(
                        MEMBER_EMAIL_NOT_FOUND_MESSAGE, ErrorType.BAD_REQUEST_ERROR, username)
        );

        MemberAuthenticationDto memberAuthenticationDto = new MemberAuthenticationDto(
                member.getEmail(),
                member.getPassword(),
                new SimpleGrantedAuthority(member.getMemberRole().getRoleName()));

        return memberAuthenticationDto;
    }

}
