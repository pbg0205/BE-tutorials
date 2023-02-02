# hibernate.query.in_clause_parameter_padding = true

### [1] org.hibernate.engine.query.spi.QueryPlanCache

 일반적으로 쿼리를 사용하는 경우, where 절에 `in clause` 를 사용하는 경우가 많다. JPA 의 구현체인 Hibernate 는 JPQL, Criteria 쿼리를
 AST(Abstract Syntax Tree)으로 파싱한다. 이 때 쿼리 컴파일 시간을 단축하기 위해 Hibernate 에서는 `QueryPlanCache` 을 사용한다.
  모든 쿼리를 실행할 때마다 `QueryPlanCache` 을 확인하여 실행 계획의 캐싱 여부를 확인해 다음 쿼리를 사용할 때 재사용한다.
 (네이티브 쿼리의 경우, 파라미터와 반환 타입을 추출하여 ParameterMetadata 에 저장한다.)

<br>

### [2] spring.jpa.properties.hibernate.query.in_clause_parameter_padding

```yaml
spring.jpa.properties.hibernate.query.in_clause_parameter_padding: true
```
`hibernate.query.in_clause_parameter_padding` 옵션은 하이버네이트 5.2.18 버전 이후에 등장한 프로퍼티 이다. 해당 프로퍼티를 **true** 로 설정하면
`in clause` 쿼리에 대해 **2의 제곱단위로 쿼리를 생성한다.** 

<br>

``` sql
-- http://localhost:8080/api/v1/students?tagNames=1,2,3,4,5 (in절 5개)

select
    student0_.id as id1_0_,
    student0_.name as name2_0_,
    student0_.tag_name as tag_name3_0_ 
from
    student student0_ 
where
    student0_.tag_name in (? , ? , ? , ? , ? , ? , ? , ?);

```
 예를 들어, `in clause` 파라미터가 5개인 경우, 파라미터 중 중복된 값을 패딩해서 파라미터를 8개인
쿼리를 생성한다. 해당 옵션을 선언하면 캐싱된 쿼리 플랜를 줄여 메모리를 절약할 수 있는 장점이 있다. 기본적으로 해당 옵션의 default value 는 `false` 이며,
기본적으로 모든 갯수의 `in clause` 를 저장하기 때문에 **memory leak 의 원인**이 될 수 있다.

<br>

_**`in clause` 파라미터가 중복되면 어떻게 동작할까?**_

MySQL 서버의 경우, IN (list) 조건이 사용되면, list 엘리멘트 값들을 메모리에 먼저 로드해서 중복 값을 제거해 가능한 형태로 만든다.

```
in (1, 2, 3, 3) -> in (1, 2, 3) 
``` 


<br>

### [3] hibernate.query.in_clause_parameter_padding 예시

```
false

[QueryPlanCache]
3개 : in (?, ?, ?)
4개 : in (?, ?, ?, ?)
5개 : in (?, ?, ?, ?, ?)
6개 : in (?, ?, ?, ?, ?, ?)
7개 : in (?, ?, ?, ?, ?, ?, ?)
8개 : in (?, ?, ?, ?, ?, ?, ?, ?)

[쿼리 실행]
3개 : in (?, ?, ?) -> (재사용)
4개 : in (?, ?, ?, ?) -> (재사용)
5개 : in (?, ?, ?, ?, ?) -> (재사용)
6개 : in (?, ?, ?, ?, ?, ?) -> (재사용)
7개 : in (?, ?, ?, ?, ?, ?, ?) -> (재사용)
8개 : in (?, ?, ?, ?, ?, ?, ?, ?) -> (재사용)

```

```
true

[QueryPlanCache]
4개 : in (?, ?, ?, ?)
8걔 : (?, ?, ?, ?, ?, ?, ?, ?)

[쿼리 실행]
3개 : in (?, ?, ?) -> 4개 : (?, ?, ?, ?) -> (재사용)
4개 : in (?, ?, ?, ?) -> 4개 : (?, ?, ?, ?) -> (재사용)
5개 : in (?, ?, ?, ?, ?) -> 8걔 : (?, ?, ?, ?, ?, ?, ?, ?) -> (재사용) 
6개 : in (?, ?, ?, ?, ?, ?) -> 8걔 : (?, ?, ?, ?, ?, ?, ?, ?) -> (재사용) 
7개 : in (?, ?, ?, ?, ?, ?, ?) -> 8걔 : (?, ?, ?, ?, ?, ?, ?, ?) -> (재사용) 
8개 : in (?, ?, ?, ?, ?, ?, ?, ?) -> 8걔 : (?, ?, ?, ?, ?, ?, ?, ?) -> (재사용) 
```

 `spring.jpa.properties.hibernate.query.in_clause_parameter_padding` 값이 `true` 일 경우, `in clause` 의 2의 제곱 단위로 중복된 값을
패딩해서 생성하기 때문에 `QueryPlanCache` 에 쿼리 플랜을 캐싱할 때도 파라미터의 갯수가 일정한 갯수로 채워지기 때문에 메모리을 효율적으로 관리할 수 있다.

<br>

### References

- [[권남] Hibernate Performance Tuning](https://kwonnam.pe.kr/wiki/java/hibernate/performance)
- [[NHN cloud] DBA와 개발자가 모두 행복해지는 Hibernate의 in_clause_parameter_padding 옵션](https://meetup.nhncloud.com/posts/211)
- [[MANTY SCUBA] Hibernate 가 메모리를 너무 많이 점유하면 hibernate.query.in_clause_parameter_padding 속성을 확인해 보세요.](https://www.manty.co.kr/bbs/detail/develop?id=98)
