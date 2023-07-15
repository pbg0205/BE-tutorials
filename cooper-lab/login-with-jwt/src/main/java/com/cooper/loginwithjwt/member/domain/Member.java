package com.cooper.loginwithjwt.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = @Index(name = "idx_member_email", columnList = "email"))
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String name;

    private String nickname;

    private String password;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    private Member(String email, String name, String nickname, String password, MemberRole memberRole) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.memberRole = memberRole;
    }

    public static Member userRole(String email, String name, String nickname, String password) {
        return new Member(email, name, nickname, password,MemberRole.USER);
    }

}
