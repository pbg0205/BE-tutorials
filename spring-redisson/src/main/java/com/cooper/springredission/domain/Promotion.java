package com.cooper.springredission.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(length = 100, nullable = false)
    private String name;

    @OneToMany(mappedBy = "promotion", cascade = {CascadeType.ALL})
    private List<Ticket> tickets = new ArrayList<>();

    public Promotion(String name) {
        this.name = name;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        ticket.setPromotion(this);
    }

}