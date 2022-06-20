## QueryDSL

- 스프링과 JPA 에서 해결하지 못하는 **복잡한 쿼리**와 **동적 쿼리** 문제를 해결할 수 있다.
- 자바코드로 작성하기 때문에 문법 오류를 **컴파일 시점에 발견**할 수 있다.
- 문법이 SQL 과 비슷하기 때문에 편리하게 작성할 수 있다.

<br>

### gradle
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

<br>

### application.yml
```
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password:
  jpa:
    show-sql: true
```
