package com.cooper.baseentityeventlistener.book.domain;

import com.cooper.baseentityeventlistener.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String isbn;

    public Book(String name, String isbn) {
        this.name = name;
        this.isbn = isbn;
    }

    public boolean updateBook(String name) {
        if (this.name.equals(name)) {
            return false;
        }

        this.name = name;
        return true;
    }

}
