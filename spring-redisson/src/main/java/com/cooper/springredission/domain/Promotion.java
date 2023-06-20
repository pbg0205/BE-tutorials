package com.cooper.springredission.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 20)
    private Long id;

    @Getter
    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private int ticketAmount;

    public Promotion(final String name, final int ticketAmount) {
        this.name = name;
        this.ticketAmount = ticketAmount;
    }

    public void decreaseTicketAmount() {
        this.ticketAmount -= 1;
    }

    public boolean soldOut() {
        return ticketAmount <= 0;
    }

    public int remainingTickets() {
        return this.ticketAmount;
    }

}
