package com.cooper.springdatajpaquerydsl.subject.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.ColumnTransformer;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subject {

    private static final String SECRET_KEY = "cooper";
    private static final String NAME_COLUMN = "name";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ColumnTransformer(
      read = "CAST(AES_DECRYPT(UNHEX(" + NAME_COLUMN + "), '" + SECRET_KEY + "') AS CHAR)",
      write = "HEX(AES_ENCRYPT(" + "?" + ", '" + SECRET_KEY + "'))"
    )
    private String name;

    public Subject(String name) {
        this.name = name;
    }
    
}
