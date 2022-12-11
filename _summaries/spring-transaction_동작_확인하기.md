### (1) 트랜잭션을 등록하는 방법
```text
2022-12-11 23:54:45.423 TRACE 8100 --- [           main] t.a.AnnotationTransactionAttributeSource : Adding transactional method 'com.cooper.springdatajpatransactional.account.service.mandatory.AccountMandatoryService.saveAccountWhenHistoryThrowException' with attribute: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
2022-12-11 23:54:45.424 TRACE 8100 --- [           main] t.a.AnnotationTransactionAttributeSource : Adding transactional method 'com.cooper.springdatajpatransactional.account.service.mandatory.AccountMandatoryService.saveAccountWithTransaction' with attribute: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
2022-12-11 23:54:45.436 TRACE 8100 --- [           main] t.a.AnnotationTransactionAttributeSource : Adding transactional method 'com.cooper.springdatajpatransactional.account.service.never.AccountHistoryNeverService.throwExceptionWhenSupports' with attribute: PROPAGATION_NEVER,ISOLATION_DEFAULT
```
- 로깅을 해서 확인해보면 transactional method 가 추가된다. 트랜잭션이 선언되는 메서드를 등록하는 빈인 AbstractFallbackTransactionAttributeSource 를 확인하면 알 수 있다.

<br>

**`AbstractFallbackTransactionAttributeSource`**
- AbstractFallbackTransactionAttributeSource 는 트랜잭션 메서드로 선언된 속성들을 등록하고 캐싱을 담당하는 컴포넌트이다.
  실제로 로그가 찍히는 부분도 확인해볼 수 있다. 리플렉션을 통해서 지정된 메서드를 호출하고 트랜잭션에 관련된 속성을 설정하여 해당 컴포넌트에 캐싱한다.

![image](https://user-images.githubusercontent.com/48561660/206912936-f93404f1-9550-46f1-9b1a-0d1b0a932f4c.png)

<br>

### (2) 트랜잭션이 동작하는 방법

**`TransactionInterceptor`** : 실제 트랜잭션이 동작을 수행하는 주체

- invokeWithinTransaction 메서드
  - 트랜잭션을 실행하기 위한 적절한 PlatformTransactionManager 을 찾는다.
  - 이전에 저장했던 TransactionAttribute 와 실행지점(joinpointIdentification) 을 찾아 AOP 기술을 통해 트랜잭션을 수행한다.

![image](https://user-images.githubusercontent.com/48561660/206912998-0857d3af-e1e8-466d-879f-55cbb0ebce11.png)
