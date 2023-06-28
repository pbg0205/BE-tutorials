# 2023-02-01

- mysql index dive

# mysql index dive

### [1] index dive

 **`index dive(random index dive)`** 는 실행 계획을 선택하기 위해 사전에 데이터를 샘플링해서 확인하는 과정을 말한다. 
 MySQL 서버의 옵티마이저는 통계 정보와 index dive 한 내용을 토대로 실행계획을 수립한다. 
 Oracle 과 PostgreSQL 의 경우 실행 계획을 캐싱해 재활용 하지만 MySQL 서버는 실행 계획을 커넥션 내에서만 캐시되고 재활용하는 방법을 사용한다.

> 이와 같은 방법은 때로 의도하지 않은 CPU 와 Disk 자원을 소모하는 경우가 있어 이와 같은 경우를 염두해두고 튜닝하는 방법을 염두해두어야 한다.
  성능을 확인해보기 위해서는 [MySQL Performance schema](https://dev.mysql.com/doc/refman/8.0/en/performance-schema.html) 또는
  [AWS Performance Insights](https://aws.amazon.com/ko/rds/performance-insights/) 이다.
    
- `MySQL Performance schema` : MySQL 서버 실행을 모니터링하는 기능 
- `AWS Performance Insights` : AWS RDS 를 모니터링할 수 있는 새로운 기능

<br>

### [2] 성능 분석 : Performance Schema, Performance Insights  

일반적으로 Performance Schema 또는 Performance Insights 에는 쿼리의 실행(executing) 자체에 많은 자원이 사용되야 때로는 통계 수집(statistics) 
단계에서 많은 자원이 소모되는 경우가 있다. **일반적으로 쿼리 실행 단계가 통계 수집 단계보다 많은 비중을 차지**하지만 `WHERE 절에 IN 을 처리할 때` 
**통계 수집 단계가 실행 단계의 비중을 역전**하는 상황이 벌어진다.

- 많은 개수의 엘리멘트가 IN (list)에 사용되는 경우 : `WHERE column_name IN (...)`
- IN (list) 조건 자체가 여러 번 사용되는 경우 : `WHERE column_name_1 IN (...) and column_name_2 IN (...)`
- IN (list) 조건들이 사용할 수 있는 인덱스가 많은 경우
    ``` mysql
    CREATE TABLE table_name (
      id BIGINT NOT NULL,
      column_name_1 VARCHAR NOT NULL,
      column_name_2 VARCHAR NOT NULL,
      ...
      PRIMARY KEY (id),
      INDEX ix_userid (column_name_1),
      INDEX ix_userid_categoryid (column_name_1, column_name_2),
      INDEX ix_userid_categoryid_fd1 (column_name_1, column_name_2, fd1),
      INDEX ix_userid_categoryid_fd2 (column_name_1, column_name_2, fd2),
      INDEX ix_userid_categoryid_fd3 (column_name_1, column_name_2, fd3)
    );
    ```

<br>

### [3] 문제 쿼리 예시

```mysql
SELECT *
FROM table_name
WHERE column_name1 IN (1,2,3,4,5, ..., 200)
  AND column_name2 IN (1,2,3,4,5, ..., 200);
```

 이와 같은 쿼리의 경우, `IN (list)` 에 포함된 `모든 조합에 대한 통계 정보를 수집한다.` 이 경우에는 200 * 200 총 40,000 개의 일치하는 레코드를
예측하는 작업을 진행한다. 만약 해당 컬럼(column_name1, column_name2) 포함하는 사용 가능한 인덱스가 존재하면 인덱스 수만큼 같은 작업을 진행한다.
**그만큼 많은 CPU 와 Disk 자원을 소모**하게 되고 **이전에 언급한 쿼리 실행 단계가 통계 수집 단계의 비중을 역전하는 상황**이 벌어진다.

<br>

### [4] 쿼리 튜닝

```
(1) FORCE INDEX (인덱스 이름)
(2) USE INDEX (인덱스 이름)
(3) SELECT /*+ INDEX ([테이블 이름] [인덱스 이름]) */
```

 인덱스를 줄이는 방법을 사용하더라도 통계 수집 기간은 크게 줄일 수 없다. 추가적으로 인덱스 최적화하는 방법도 있지만 만족스러운 결과를 얻지 못하는 경우에
3가지 방법이 있다. 3가지 방법 모두 특정 인덱스 사용을 유도한다는 공통점이 있다. 

하지만 차이점도 있다. 2,3 번의 경우, 옵티마이저가 해당 인덱스의 Index dive 를 거쳐 효율적인 경우 다른 인덱스를 무시하고 쿼리 실행 계획을 수립하는 
반면, 1번은 2, 3번과 유사하게 동작하지만 아래의 조건을 충족하면 `Index dive` 를 건너뛴다. 1,2번은 MySQL 버전에 관계없고, 3번은 5.7 이상일 경우에만 사용할 수 있다.

1.  단일 테이블 쿼리인 경우
2.  단일 인덱스에만 FORCE INDEX 힌트를 사용한 경우
3.  SubQuery 가 없는 경우
4.  Fulltext 인덱스가 사용되지 않는 경우
5.  GROUP BY와 DISTINCT 가 없는 경우
6.  ORDER BY 절이 없는 경우

<br>

### [5] 시스템 변수를 이용한 튜닝
```
(1) eq_range_index_dive_limit
(2) range_optimizer_max_mem_size
```
`eq_range_index_dive_limit` 은 **동등비교 조건**의 개수를 기준으로, 실행 계획 수립 단계의 Index Dive 를 할지 단순히 통계 정보만 활용할지 여부를 결정한다.
예를 들어, eq_range_index_dive_limit=200 으로 설정된 경우, **MySQL 서버 옵티마이저는 동등 비교 회수가 200을 넘으면 Index Dive 를 실행하지 않고
통계 정보만을 이용해서 실행 계획을 수립**한다. 하지만, **범위 조건에는 영향을 못 미치기 때문에 주의**해야 한다.

 `range_optimizer_max_mem_size` 은 **MySQL 서버는 IN (list) 조건이 사용되면, list 엘리멘트 값들을 메모리에 먼저 로드해서 중복 값을 제거하고 
사용 가능한 형태로 확장(fan-out) 할 때, 할당할 메모리를 설정**한다. range_optimizer_max_mem_size **시스템 변수에 설정된 메모리 크기를 초과하면 
MySQL 서버는 해당 실행 계획을 포기한다.** 즉, 해당 인덱스를 사용하지 않고 다른 최적의 실행 계획을 선택한다. 최악의 경우에는 테이블 풀스캔을 할 수 있다.

그런데 `FORCE INDEX` 힌트를 사용하면 range_optimizer_max_mem_size 시스템 변수에 설정된 메모리 공간을 넘어서는 경우에도 선언한 
인덱스를 활용한 실행 계획을 사용할 수 있다. 

<br>

### [6] 결론

**튜닝을 고려해야 하는 경우,**
```
(1) IN (list)에 많은 엘리멘트가 사용
(2) 하나의 쿼리에서 인덱스를 사용하는 IN (list) 조건이 여러 번일 경우
```

**튜닝을 신경을 쓰지 않아도 되는 경우,**

```
(1) WHERE 절에 하나의 IN (list)조건만 사용되는 경우
(2) IN (list) 에 사용된 엘리멘트의 개수가 많지 않은 경우
(3) IN (list) 조건이 인덱스를 사용하지 못하는 경우
```

<br>

### References

- [[당근마켓 팀블로그] Index Dive 비용 최적화](https://medium.com/daangn/index-dive-%EB%B9%84%EC%9A%A9-%EC%B5%9C%EC%A0%81%ED%99%94-1a50478f7df8)

<br>

### To Do List
- [ ] MySQL docs Performance schema 읽어보기
- [ ] 우형에 AWS Performance Insights 에 관한 글 한번 읽어볼 것
    - [[우형 기술블로그] Performance Insight 써도 돼요?](https://techblog.woowahan.com/2593/)
