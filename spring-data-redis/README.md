# spring-data-redis-example


## 1. Redis(REmote DIctionary Server) ??

![image](https://user-images.githubusercontent.com/48561660/206840871-ca600843-735a-4a72-b678-6fa4f266b48a.png)

- Key-Value 기반 NoSQL 인메모리 데이터베이스 오픈소스
- 예시 : 캐싱, 세션 관리, pub/sub 및 순위표 등이 있다.
- 장점
    1. **속도** : 데이터를 메모리에 데이터를 캐싱하기 때문에 디스크에 접근하는 속도보다 훨씬 빠르다 (대략 10^5 정도)
    2. **대기열** : List 구조를 통해 자동 작업, 차단 기능, 메세지 브로커 역할을 한다.
    3. **PUB/SUB** : 발행/구독 모델을 구현할 수 있어 고성능 채팅방, 코멘트 스트림 및 서버 상호 통신 지원
    4. **개발 편의성** : 자료구조를 제공해 개발에 있어 편리하다.
    5. **복제 및 지속성, 안정성** : 스냅샷 제공 및 AOF 제공
        - 스냅샷 : Redis 데이터 세트를 디스크로 복사
        - AOF(Append Only File) : 디스크에 저장하는 기능

<br>

## 2. Redis 대표적인 자료구조
1. **string** : binary-safe 한 기본적인 key-value 구조
2. **list** : String element의 모음, 순서는 삽입된 순서를 유지하며 기본적인 자료구로 Linked List를 사용
3. **set** : 유일한 값들의 모임인 자료구조, 순서는 유지되지 않음
4. **sorted set** : Sets 자료구조에 score라는 값을 추가로 두어 해당 값을 기준으로 순서를 유지
5. **hashes** : 내부에 key-value 구조를 하나더 가지는 Reids 자료구조
6. **Bit arrays(bitMaps)** : bit array를 다를 수 있는 자료구조
7. **HyperLogLogs** : HyperLogLog는 집합의 원소의 개수를 추정하는 방법, Set 개선된 방법
8. **Streams** : Redis 5.0 에서 Log나 IoT 신호와 같이 지속적으로 빠르게 발생하는 데이터를 처리하기 위해서 도입된 자료구조

(참고 : https://sabarada.tistory.com/134)

<br>

## 3. spring data redis 설정 방법

### (1) dependency gradle

```groovy
dependencies {
    //...
    implementation 'org.springframework.boot:spring-boot-starter-data-redis'
    //...
}
```

### (2) java configuration

```java
import com.example.springdataredisexample.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories //KeyValueRepository 를 사용하고 싶은 경우 선언 
@RequiredArgsConstructor
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, Member> redisTemplate() {
        RedisTemplate<String, Member> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // 문자열로 직렬화
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());// 객체를 json 변환해서 직렬화
        return redisTemplate;
    }

}
```

### (3) 자료구조에 따라 RedisTemplate 핸들링하는 법
```java
// string
redisTemplate.opsForValue()

// list
redisTemplate.opsForList()

// set
redisTemplate.opsForSet()

// sorted set
redisTemplate.opsForZSet()

// hashes
redisTemplate.opsForHash()

// hyperloglogs
redisTemplate.opsForHyperLogLog()

// streams
redisTemplate.opsForStream()
```

- 핸들링은 `org.springframework.data.redis.core` 에 친절하게 docs 링크와 연결되어 있어서 찾아보면서 공부하자.
![image](https://user-images.githubusercontent.com/48561660/206842024-5f5113d6-91dd-4e79-8081-947a6e966f54.png)

<br>

## References

- [redis.io] commands : https://redis.io/commands/
- [NHN Cloud] 개발자를 위한 레디스 튜토리얼1 : https://meetup.toast.com/posts/224
- [amazon] Redis란 무엇입니까? : https://aws.amazon.com/ko/elasticache/what-is-redis/
