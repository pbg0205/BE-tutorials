### Index

---

- [QueryDSL](#QueryDSL)
- [Configuration](#Configuration)
  - [gradle](#gradle)
  - [application.yml](#application-yml)
- [JPQL 대신 Querydsl 을 사용하는 이유](JPQL-대신-Querydsl-을-사용하는-이유)
- [결과 조회](결과-조회)

# QueryDSL

- 스프링과 JPA 에서 해결하지 못하는 **복잡한 쿼리**와 **동적 쿼리** 문제를 해결할 수 있다.
- 자바코드로 작성하기 때문에 문법 오류를 **컴파일 시점에 발견**할 수 있다.
- 문법이 SQL 과 비슷하기 때문에 편리하게 작성할 수 있다.

<br>

## Configuration

### gradle

---

```groovy
buildscript {
    ext {
        queryDslVersion = "5.0.0"
    }
}
plugins {
    id 'org.springframework.boot' version '2.6.2'
    id 'io.spring.dependency-management' version '1.0.11.RELEASE'
    //querydsl 추가
    id "com.ewerk.gradle.plugins.querydsl" version "1.0.10"
    id 'java'
}
group = 'com.example'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'
configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}
repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    
    //querydsl 추가
    implementation "com.querydsl:querydsl-jpa:${queryDslVersion}"
    annotationProcessor "com.querydsl:querydsl-apt:${queryDslVersion}"
    runtimeOnly 'com.h2database:h2'
    
     testImplementation('org.springframework.boot:spring-boot-starter-test') {
        exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
     }
}
test {
 useJUnitPlatform()
}

//querydsl 추가 시작
def querydslDir = "$buildDir/generated/querydsl"
querydsl {
 jpa = true
 querydslSourcesDir = querydslDir
}
sourceSets {
 main.java.srcDir querydslDir
}
configurations {
 querydsl.extendsFrom compileClasspath
}
compileQuerydsl {
 options.annotationProcessorPath = configurations.querydsl
}
//querydsl 추가 끝
```

1. QueryDSL 라이브러리
   - querydsl-apt: querydsl 관련 코드 생성 기능 제공
   - querydsl-jpa : querydsl 라이브러리
2. 스프링 부트 라이브러리
   - **spring-boot-starter-web**
     - spring-boot-starter-tomcat: 톰캣(웹서버)
     - spring-web-mvc: 스프링 웹 MVC
   - **spring-boot-starter-data-jpa**
     - spring-boot-starter-aop
     - spring-boot-starter-jdbc
       - HikariCP 커넥션 풀 (부트 2.0 기본)
     - hibernate + JPA: 하이버네이트 + JPA
     - spring-data-jpa: 스프링 데이터 JPA
   - **spring-boot-starter(공통): 스프링 부트 + 스프링 코어 + 로깅** 
     - spring-boot
         - spring-core 
     - spring-boot-starter-logging 
       - logback, slf4j
   - spring-boot-starter-test
     - junit: 테스트 플레임워크, 부트 2.2부터 junit5(jupiter) 사용
     - mock: 목 라이브러리
     - assertj: 테스트 코드를 좀 더 편하게 작성하게 도와주는 라이브러리

<br>

### application.yml

---

```
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password:
  jpa:
    show-sql: true
```

<br>

### JPQL 대신 Querydsl 을 사용하는 이유

---

- JPQL 은 문자이기 때문에 런타임 에러를 반환하는 반면, **컴파일 시점에서 오류를 반환한다.**
- JPQL 은 직접 파라미터를 바인딩하는 반면, **파라미터 바인딩을 자동 처리**한다.

<br>

### 결과 조회

---

- **fetch()** : 리스트 조회, 데이터 없으면 빈 리스트 반환
- **fetchOne()** : 단 건 조회 
  - 결과가 없으면 : null 
  - 결과가 둘 이상이면 : com.querydsl.core.NonUniqueResultException
- **fetchFirst()** : limit(1).fetchOne()
- **fetchResults()** : 페이징 정보 포함, total count 쿼리 추가 실행
- **fetchCount()** : count 쿼리로 변경해서 count 수 조회