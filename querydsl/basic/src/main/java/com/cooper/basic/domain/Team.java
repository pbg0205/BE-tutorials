package com.cooper.basic.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Members members;

    private Team(String name) {
        this.name = name;
    }

    public static Team create(String name) {
        return new Team(name);
    }

    public void addMember(Member member) {
        members.add(member);
        member.setTeam(this);
    }

    public void removeMember(Member member) {
        members.remove(member);
        member.setTeam(null);
    }

}
