## Batch Application

- 배치는 대용량의 데이터를 일괄 처리를 지원하는 배치 어플리케이션이다.
- 배치 어플리케이션은 5가지 조건을 만족해야 한다.
    1. 대용량 데이터 : 대량의 데이터를 가져오거나, 전달, 연산 등을 처리할 수 있어야 한다.
    2. 자동화 : 사용자의 개입없이 자동으로 실행되어야 한다.
    3. 견고성 : 잘못된 데이터를 충돌/중단 없이 처리할 수 있어야 한다.
    4. 신뢰성 : 에러가 발생한 지점을 로깅, 알림을 통해 원인을 추적할 수 있어야 한다.
    5. 성능 : 지정된 시간 안에 처리를 완료하거나 다른 어플리케이션의 방해을 받지 않고 수행되어야 한다.

<br>

## Spring Batch

- Accenture 과 Spring Source 공동 작업을 통해 만들어진 배치 처리 프레임워크 이다.
- Spring Batch 는 Spring 에서 제공하는 기술과 연과지어 다양한 기능을 제공한다.
- Spring Quartz 는 스케줄링을 지원하는 역할이며, Spring Batch 를 상호 보완하는 역할을 한다.
    (주로 Qautz + Batch 조합을 사용한다)

| DataSource | 기술 | 설명 |
| --- | --- | ----- |
| Database | JDBC | 페이징, 커서, 일괄 업데이트 등 사용 가능 |
| Database | Hibernate | 페이징, 커서 사용 가능 |
| Database | JPA | 페이징 사용 가능 (현재 버전에서는 커서 없음) |
| File | Flat File | 지정한 구분자로 파싱 지원 (e.g. csv 파일) |
| File | XML | XML 파일 지원 |

<출처 : [[jojoldu] 1. Spring Batch 가이드 - 배치 어플리케이션이란?](https://jojoldu.tistory.com/324)>

<br>


## References

- [[jojoldu] 1. Spring Batch 가이드 - 배치 어플리케이션이란?](https://jojoldu.tistory.com/324)
- [Spring Batch - Reference Documentation - Spring Batch Introduction](https://docs.spring.io/spring-batch/docs/current/reference/html/spring-batch-intro.html#spring-batch-intro)