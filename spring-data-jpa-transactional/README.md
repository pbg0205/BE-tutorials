# transactional-practice

## 1. what is @Transactional?

- 메서드를 DB Transaction 으로 감싸주는 어노테이션
- 옵션으로 propagation, isolation, read-only, rollback 을 설정할 수 있다. 또한 transaction manager 를 명시할 수 있다.

<br>

## 2. @Transactional Implementation Details

- 스프링은 트랜잭션을 생성, 커밋, 롤백을 하기 위해 프록시를 생성하거나 바이트 코드를 조작한다.
- 만약 프록시를 사용할 경우, @Transactional 은 접근 제한자가 public 인 메서드에서만 동작한다.
  (만약 public 이 아닌 메서드는 에러없이 동작하지 않으므로 주의해야 한다.)
- 결국, @Transactional 이 존재하면 스프링은 바이트 코드를 조작해 트랜잭션 관리 코드를 래핑한다.

<br>

## 3. Transaction Propagation

| propagation 종류 | 설명                                                                                                                         |
|:--------------:|----------------------------------------------------------------------------------------------------------------------------|
|    REQUIRED    | 부모 트랜잭션이 존재하면 부모 트랜잭션 내에서 동작하고, 없으면 새로운 트랜잭션을 생성한다.                                                                        | 
|    SUPPORTS    | 부모 트랜잭션이 존재하면 부모 트랜잭션 내에서 동작하고, 부모 트랜잭션이 없으면 트랜잭션없이 동작한다.                                                                  |
|   MANDATORY    | 부모 트랜잭션이 존재하면 부모 트랜잭션 내에서 동작하고, 부모 트랜잭션이 없으면 예외를 발생한다.                                                                     |
|     NEVER      | 부모 트랜잭션이 존재하면 예외를 발생하고, 부모 트랜잭션이 없으면 트랜잭션 없이 동작한다.                                                                         |
| NOT_SUPPORTED  | 부모 트랜잭션이 존재하면 일시 정지하고, 부모 트랜잭션이 없으면 트랜잭션 없이 동작한다.                                                                          |
|  REQUIRES_NEW  | 부모 트랜잭션의 존재하면 일시 정지하고, 새로운 트랜잭션을 생성한다. (트랜잭션이 독립적으로 동작한다.)                                                                 |
|     NESTED     | Spring 은 트랜잭션이 존재하는지 확인하고 존재하는 경우 저장점을 표시한다. 즉, 비즈니스 로직 실행에서 예외가 발생하면 트랜잭션이 이 저장 지점으로 롤백됩니다. 활성 트랜잭션이 없으면 REQUIRED처럼 작동한다. |

<br>

### References

- https://www.baeldung.com/spring-transactional-propagation-isolation
