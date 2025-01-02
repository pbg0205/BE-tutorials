package com.example.springdataredisexample.study;

import com.example.springdataredisexample.members.domain.Member;
import com.example.springdataredisexample.utils.MemberRedisListUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MemberRedisListUtilsTest {

    @Autowired
    private MemberRedisListUtils memberRedisListUtils;

    @DisplayName("lpush & lpop 테스트")
    @Test
    void lpushAndLpopTest() {
        String key = "member";

        memberRedisListUtils.removeList(key);

        memberRedisListUtils.leftPush(key, Member.of("member1"));
        memberRedisListUtils.leftPush(key, Member.of("member2"));
        memberRedisListUtils.leftPush(key, Member.of("member3"));

        List<Member> leftPushedMembers = memberRedisListUtils.getList(key);
        leftPushedMembers.forEach(System.out::println);

        memberRedisListUtils.leftPop(key);

        List<Member> leftPopMembers = memberRedisListUtils.getList(key);
        leftPopMembers.forEach(System.out::println);

        memberRedisListUtils.removeList(key);
    }

    @DisplayName("rpush & rpop 테스트")
    @Test
    void rpushAndRpopTest() {
        String key = "member";

        memberRedisListUtils.removeList(key);

        memberRedisListUtils.rightPush(key, Member.of("member1"));
        memberRedisListUtils.rightPush(key, Member.of("member2"));
        memberRedisListUtils.rightPush(key, Member.of("member3"));

        List<Member> rightPushedMembers = memberRedisListUtils.getList(key);
        rightPushedMembers.forEach(System.out::println);

        memberRedisListUtils.rightPop(key);

        List<Member> members = memberRedisListUtils.getList(key);
        members.forEach(System.out::println);

        memberRedisListUtils.removeList(key);
    }

    @DisplayName("시작 from list tail 값을 to list tail 로 이동한다")
    @Test
    void moveMethodTest() {
        String key1 = "member1";
        String key2 = "member2";

        memberRedisListUtils.removeList(key1);
        memberRedisListUtils.removeList(key2);

        memberRedisListUtils.rightPush(key1, Member.of("member1"));
        memberRedisListUtils.rightPush(key1, Member.of("member2"));
        memberRedisListUtils.rightPush(key1, Member.of("member3"));

        memberRedisListUtils.rightPush(key2, Member.of("member4"));
        memberRedisListUtils.rightPush(key2, Member.of("member5"));
        memberRedisListUtils.rightPush(key2, Member.of("member6"));

        memberRedisListUtils.moveFromTailToTail(key1, key2);

        memberRedisListUtils.getList(key1).forEach(System.out::println);
        System.out.println();
        memberRedisListUtils.getList(key2).forEach(System.out::println);

        memberRedisListUtils.removeList(key1);
        memberRedisListUtils.removeList(key2);
    }

}
