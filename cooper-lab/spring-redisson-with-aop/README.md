# Redisson, Spring AOP, SpelParser 를 이용한 분산락 적용 방법

> [[Kurly Tech Blog] 풀필먼트 입고 서비스팀에서 분산락을 사용하는 방법 - Spring Redisson](https://helloworld.kurly.com/blog/distributed-redisson-lock/)
> 을 참고하여 작성한 내용.

# 컴포넌트 정리

1. `DistributedLockAop` : `@DistributedLock` annotation 을 매핑하여 분산락을 설정, 해제하는 Aspect 클래스  
2. `CustomSpringELParser` : SpelExpressionParser 를 이용해 메서드의 파라미터 값을 가져와 key 값을 spel 형식에 매핑하여 값을 반환하는 유틸 클래스   
3. `AopForTransaction` : Tx commit 이후에 Lock 을 해제하기 위한 컴포넌트  

# 내용 정리

1. 분산 락(Lock) 은 락에 대한 정보를 공통적으로 보관하는 영역에 보관하여 임계 영역을 접근 여부를 확인하여 동시성 이슈를 해결할 수 있는 방법.
2. **분산락을 해제할 때 는 데이터 정합성(Data Integrity) 을 위해 `Tx commit 이후 시점`에 락을 해제하라.**

# References

- [[Kurly Tech Blog] 풀필먼트 입고 서비스팀에서 분산락을 사용하는 방법 - Spring Redisson](https://helloworld.kurly.com/blog/distributed-redisson-lock/)
- [[hudi.blog] Redis로 분산 락을 구현해 동시성 이슈를 해결해보자!](https://hudi.blog/distributed-lock-with-redis/)
