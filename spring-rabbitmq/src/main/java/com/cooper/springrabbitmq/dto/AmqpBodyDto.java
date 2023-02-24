package com.cooper.springrabbitmq.dto;

public class AmqpBodyDto {

    private String name;
    private String content;

    public AmqpBodyDto() {
    }

    public AmqpBodyDto(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "AmqpBodyDto{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
