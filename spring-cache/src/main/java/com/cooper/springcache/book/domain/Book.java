package com.cooper.springcache.book.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Book {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    private String title;

    protected Book() {
    }

    private Book(String title) {
        this.title = title;
    }

    public static Book create(String name) {
        return new Book(name);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}
