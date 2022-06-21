package com.cooper.basic.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Members {

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    public void add(Member member) {
        members.add(member);
    }

    public void remove(Member member) {
        members.remove(member);
    }

}
