# 3장. SQL 튜닝의 실행 계획 파헤치기

- [업무에 바로쓰는 SQL 튜닝](https://product.kyobobook.co.kr/detail/S000001810409) 내용에 관해 학습한 내용을 정리한 페이지

## 1. MySQL Setting 하기

### 1. MySQL 8.0 container 실행

```bash
docker run -d \
--name mysql_tuning \
-p 3306:3306 \
-e MYSQL_ROOT_PASSWORD=cooper2021 \
mysql:8.0 \
--character-set-server=utf8mb4 \ 
--collation-server=utf8mb4_unicode_ci \
```

- character-set-server, collation-server 는 mysql8.0 뒤에 붙여야 한다.

<br>

### 2. 데이터 세팅하기

1. Repository(https://github.com/7ieon/SQLtune) 접속해서 ZIP 파일로 다운로드
2. MySQL docker container 에 샘플데이터 copy
    ```bash
    # docker exec -i [container name] -u root -p[Password] < data_setting.sql
    # docker cp [디렉토리 경로] mysql_tuning:/sapmle_data
    docker cp /Users/cooper/Downloads/SQLtune-main mysql_tuning:/sample_data
    ```
3. MySQL docker container 에서 data_setting.sql 파일 실행 
    ```bash
    # (1) docker container /bin/bash 실행
    docker exec -it mysql_tuning /bin/bash
    
    # data_setting.sql 파일 실행
    mysql -uroot -pcooper2021 < /sample_data/data_setting.sql
    ```
4. 데이터 정상 migration 확인
   ```SQL
   show databases;
   use tuning;
   show tables; # 급여, 부서, 부서관리자, 부서사원_매핑, 사원, 사원출입기록, 직급
   ```
5. 테이블 확인
   
   ![img.png](/_dev-books/업무에_바로_쓰는_SQL_튜닝/images/img01.png)

<br>

## 3. 싱행 계획 수행

### 1. 기본 실행 계획 수행 키워드
```sql
EXPLAIN SQL 문;
DESCRIBE SQL 문;
DESC SQL 문;
```
- 기본 실행 계획 조회 시 id, select_type, table, type, key 정보가 출력된다.
- MySQL 의 실행 계획에서는 partitions, filtered 열이 추가된다.

<br>

### 2. 기본 실행 계획 항목 분석

1. `id` : 실행 순서를 표시하는 숫자. 작을수록 먼저 수행되는 것
2. `select_type` : SELECT 문의 유형을 출력하는 항목
3. `table` : 테이블명을 표시하는 항목
4. `partitions` : 실행 계획 부가 정보, 데이터가 저장된 논리적인 영역을 표시하는 항목
5. `type` : 테이블의 데이터를 어떻게 찾을지에 관한 정보를 제공하는 항목
6. `possible_keys` : 옵티마이저가 SQL 문을 최적화하고자 사용할 수 있는 인덱스 목록을 출력
7. `key` : 옵티마이저가 SQL 문을 최적화하고자 사용한 기본키(PK) 또는 인덱스명
8. `key_len` : 사용한 인덱스의 바이트 수를 의미.
9. `ref`: 테이블 조인을 수행할 때 어떤 조건으로 해당 테이블에 엑세스되었는지 알려주는 정보
10. `rows`: SQL 문을 수행하고자 접근하는 데이터의 모든 row 수를 나타내는 예측 항목
11. `filtered` : SQL 문을 통해 DB 엔진으로 가져온 데이터 대상으로 필터 조건에 따라 어느 정도의 비율로 데이터를 제거했는지를 의미하는 항목 (단위: %)
12. `extra` : SQL 문을 어떻게 수행할 것인지에 관한 추가 정보를 보여주는 항목
 
