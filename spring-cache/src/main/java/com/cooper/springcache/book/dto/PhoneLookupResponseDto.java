package com.cooper.springcache.book.dto;

public class PhoneLookupResponseDto {

    private String id;
    private String name;

    public PhoneLookupResponseDto() {
    }

    public PhoneLookupResponseDto(String id, String name) {
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
