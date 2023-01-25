package com.cooper.springcache.book.dto;

public class BookLookupResponseDto {

    private String id;
    private String name;

    public BookLookupResponseDto() {
    }

    public BookLookupResponseDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
