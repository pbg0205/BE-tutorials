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
   ![img.png](/images/img01.png)

<br>
