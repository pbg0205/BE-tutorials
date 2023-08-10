package com.cooper.springcache.book.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Phone {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    private String title;

    protected Phone() {
    }

    private Phone(String title) {
        this.title = title;
    }

    public static Phone create(String name) {
        return new Phone(name);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}
