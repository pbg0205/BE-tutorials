package com.cooper.springbootlogback.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST)
    private List<Member> members = new ArrayList<>();

    private String name;

    protected Team() {
    }

    public Team(String name) {
        this(null, name);
    }

    public Team(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addMember(Member member) {
        member.changeTeam(this);

        if (!this.members.contains(member)) {
            this.members.add(member);
        }
    }

    public Long getId() {
        return id;
    }

    public List<Member> getMembers() {
        return members;
    }

    public String getName() {
        return name;
    }
}
