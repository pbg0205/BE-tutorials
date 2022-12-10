package com.example.springdataredisexample.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor
@RedisHash(value = "member", timeToLive = 20)
public class Member implements Serializable {

    @Id
    private String id;

    private String name;

    private Member(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public static Member of(String name) {
        return new Member(name);
    }

}
