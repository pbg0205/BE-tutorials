# Redisson, Spring AOP, SpelParser 를 이용한 분산락 적용 방법

> [[Kurly Tech Blog] 풀필먼트 입고 서비스팀에서 분산락을 사용하는 방법 - Spring Redisson](https://helloworld.kurly.com/blog/distributed-redisson-lock/)
> 을 참고하여 작성한 내용.

## 1. RMS(Receiving Management System - 입고관리 시스템) 의 동시성 문제

1. 카프카로 동시에 들어오는 중복된 발주를 수신하는 경우
2. 검수/검품 이슈 등록 시 더블 클릭, 네트워크 이슈로 인해 중복된 요청이 동시에 들어오는 경우
3. 이동 출고시 여러 작업자가 CTA를 동시에 클릭하여 잘못된 재고 트랜잭션이 생성되는 경우

## 2. Redisson library 를 선정한 이유

1. **Redisson Library 의 장점**
   1. `Pub/Sub 방식`을 이용해 락이 해제될 경우 락을 subscribe 하여 락 획득을 시도하여 Redis 의 부하를 줄일 수 있다.
   2. Lock interface 를 지원해 `retry`, `timeout` 과 같은 기능을 지원한다.
2. **Lettuce 을 이용한 분산락 구현의 단점**
   1. 분산락을 직접 구현해야 한다.
   2. `setnx`, `setex` 명령어를 이용해 스핀락 방식이므로 지속적으로 Redis 에게 락 해제 여부를 물어봐 Redis 부하가 커진다.

## 3. 분산 락을 손쉽게 사용하고자 한 노력

1. 비즈니스 로직이 오염되지 않게 분리해서 사용한다.
2. waitTime, leaseTime 을 커스텀하게 지정하고 싶다.
3. 락 이름(lock name) 을 사용자로 부터 커스텀하게 처리하고 싶다.
4. 추가 요구사항에 대해 공통으로 관리하고 싶다.

> 이를 해결하기 위해 Spring AOP 를 이용한 분산락 컴포넌트를 만들기로 함.

# 4. 컴포넌트 정리

1. `DistributedLockAop` : `@DistributedLock` annotation 을 매핑하여 분산락을 설정, 해제하는 Aspect 클래스  
2. `CustomSpringELParser` : SpelExpressionParser 를 이용해 메서드의 파라미터 값을 가져와 key 값을 spel 형식에 매핑하여 값을 반환하는 유틸 클래스   
3. `AopForTransaction` : Tx commit 이후에 Lock 을 해제하기 위한 컴포넌트  

# 5. 내용 정리

1. 분산 락(Lock) 은 락에 대한 정보를 공통적으로 보관하는 영역에 보관하여 임계 영역을 접근 여부를 확인하여 동시성 이슈를 해결할 수 있는 방법.
2. **분산락을 해제할 때 는 데이터 정합성(Data Integrity) 을 위해 `Tx commit 이후 시점`에 락을 해제하라.**

# References

- [[Kurly Tech Blog] 풀필먼트 입고 서비스팀에서 분산락을 사용하는 방법 - Spring Redisson](https://helloworld.kurly.com/blog/distributed-redisson-lock/)
- [[hudi.blog] Redis로 분산 락을 구현해 동시성 이슈를 해결해보자!](https://hudi.blog/distributed-lock-with-redis/)
