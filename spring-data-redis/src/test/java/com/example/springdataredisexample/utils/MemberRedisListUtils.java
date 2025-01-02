package com.example.springdataredisexample.utils;

import com.example.springdataredisexample.members.domain.Member;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class MemberRedisListUtils {

    @Qualifier("memberRedisTemplate")
    private final RedisTemplate<String, Member> redisTemplate;

    public MemberRedisListUtils(RedisTemplate<String, Member> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long rightPush(String key, Member member) {
        return redisTemplate.opsForList().rightPush(key, member);
    }

    public Long leftPush(String key, Member member) {
        return redisTemplate.opsForList().leftPush(key, member);
    }

    public Member leftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public Member rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    public Long remove(String key, long count, Member member) {
        return redisTemplate.opsForList().remove(key, count, member);
    }

    public Member index(String key, int index) {
        return redisTemplate.opsForList().index(key, index);
    }

    public Member rightPopAndLeftPush(String sourceKey, String destinationKey) {

        return redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
    }

    public List<Member> getList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public void removeList(String key) {
        redisTemplate.expire(key, Duration.ofMillis(0));
    }

    public Member moveFromTailToTail(String sourceKey, String destinationKey) {
        ListOperations.MoveFrom<String> from = ListOperations.MoveFrom.fromTail(sourceKey);
        ListOperations.MoveTo<String> to = ListOperations.MoveTo.toTail(destinationKey);
        return redisTemplate.opsForList().move(from, to, Duration.ofMinutes(5));
    }

}
