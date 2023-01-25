package com.cooper.springcache.book.dto;

public class BookCreateResponseDto {

    private String id;
    private String title;

    public BookCreateResponseDto(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
