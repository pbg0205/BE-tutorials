package com.cooper.springredission.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15, nullable = false)
    private Long price;

    @Column(length = 20, nullable = false)
    private Long promotionId;

    public Ticket(final Long price, final Long promotionId) {
        this.price = price;
        this.promotionId = promotionId;
    }

}
