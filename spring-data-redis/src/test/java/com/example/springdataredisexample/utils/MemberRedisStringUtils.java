package com.example.springdataredisexample.utils;

import com.example.springdataredisexample.members.domain.Member;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MemberRedisStringUtils {

    private final RedisTemplate<String, Member> redisTemplate;

    public MemberRedisStringUtils(RedisTemplate<String, Member> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Member set(Member member) {
        redisTemplate.opsForValue().set(member.getId(), member);
        return redisTemplate.opsForValue().get(member.getId());
    }

    public Optional<Member> get(String memberId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(memberId)); // key 값에 대한 value 가 존재하지 않으면 null 반환
    }

    public Optional<Member> getAndDelete(String memberId) {
        return Optional.ofNullable(redisTemplate.opsForValue().getAndDelete(memberId)); // key 값에 대한 value 가 존재하지 않으면 null 반환
    }

    public void list() {
        String listKey = "member-key";
        Member member01 = Member.of("member01");
        Member member02 = Member.of("member02");
        Member member03 = Member.of("member03");
        Member member04 = Member.of("member04");

        redisTemplate.opsForList().rightPush(listKey, member01);
        redisTemplate.opsForList().rightPush(listKey, member02);
        redisTemplate.opsForList().rightPush(listKey, member03);
        redisTemplate.opsForList().rightPush(listKey, member04);

        List<Member> members = redisTemplate.opsForList().range(listKey, 0, -1);
        members.forEach(System.out::println);
    }

}

