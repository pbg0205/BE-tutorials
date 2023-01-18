## 4장. 악성 SQL 튜닝으로 초보자 탈출하기

## 1. 실무적인 튜닝 절차

### (1) SQL 문과 실행결과 파악

- 결과 및 소요시간 확인
- 조인/서브쿼리 구조
- 동등/범위 조건

<br>

### (2) 가시적/비가시적

`가시적`

1. 테이블 데이터 건수
2. SELECT 절 컬럼 분석
3. 조건절 컬럼 분석
4. 그룹핑/정렬 검색

`비가시적`

1. 실행 계획
2. 인덱스 현황
3. 데이터 변경 추이
4. 업무적 특징

<br>

### (3) 튜닝 방향 판단 & 개선방향/적용

<br>

## 2. 간단한 쿼리 개선 사항

### (1) 기본 키를 번형하는 나쁜 SQL 문

`튜닝 이전`

```mysql
EXPLAIN
SELECT *
FROM 사원
WHERE SUBSTRING(사원번호, 1, 4) = 1100
  AND LENGTH(사원번호) = 5;
```
![img.png](/_dev-books/업무에_바로_쓰는_SQL_튜닝/images/img02.png)
 
1. `type : ALL` - 테이블 풀 스캔 방식
   - 처음부터 끝까지 스캔하는 방식
   - 직접 테이블을 접근하는 방식이기 때문에 비효율적이다.
   - 사원번호를 사용할 수 있는 인덱스가 있기 떄문에 사원번호 값을 그대로 유지해서 개선할 수 있다.  

```mysql
show index from 사원;
```
![img03.png](/_dev-books/업무에_바로_쓰는_SQL_튜닝/images/img03.png)

<br>

`튜닝 이후`

```mysql
EXPLAIN
SELECT *
FROM 사원
WHERE 사원번호 BETWEEN 11000 AND 11009;
```
![img04.png](/_dev-books/업무에_바로_쓰는_SQL_튜닝/images/img04.png)

1. `type : range` : 테이블 내에 연속된 데이터 범위를 조회하는 유형 (e.g. <, >, IS NULL, BETWEEN ...)
2. `extra : Using Where` : WHERE 절의 필터 조건을 사용해 MySQL 엔진으로 가져온 데이터를 추출하는 것을 의미
3. `filtered` : 스토리지 엔진에서 가져온 데이터를 대상으로 필터 조건에 따라 제거한 데이터의 비율을 퍼센트로 표현한 항목
  - WHERE 사원번호 between 1 and 10 : 100건의 데이터를 조회해 그 중 10건으로 10% 필터링 

<br>
