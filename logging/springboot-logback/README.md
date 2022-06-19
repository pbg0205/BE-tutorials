# [우테코-테코톡] 로깅 정리

[1. Logging](#1-Logging)
- [(1) Logging?](#1-logging-1)
- [(2) Log Level](#2-log-level)
- [(3) SLF4J (Simple Logging Facade for Java)](#3-slf4j-simpe-logging-facade-for-java))

[2. Logback](#2-Logback)
- [(1) 구조](#1-구조)
- [(2) 설정요소](#2-설정요소)
- [(3) 실습 요구사항 및 정리](#3-실습-요구사항-및-정리)

## 1. Logging

### (1) Logging?

- 로깅은 **프로그램 동작 시 발생하는 모든 일을 기록하는 행위**를 말한다. 여기서 말하는 모든 일은 최소한의 목적을 말한다. 
예를 들어, **서비스 동작 상태 또는 장애(exception, error)에 관한 상황**을 말한다.
서비스 동작 상태는 시스템 로딩, HTTP 통신, 트랜잭션, DB요청, 의도를 가진 Exception을 말한다.<br>

- 로깅은 프로젝트 성격과 팀에 맞게 로깅 시점은 때에 달라야 한다. 똑같은 서비스라도 요구사항이 달라질 수도 있다.
  앞서 말한 내용을 우선시 생각하고 적용하도록 하자.


<br>

### (2) Log Level

| level | 설명 |
| --- | --- |
| Fatal | 매우 심각한 에러. 프로그램이 종료되는 경우가 많음. |
| Error | 의도하지 않은 에러가 발생한 경우 |
| Warn | 에러가 될 수 있는 잠재적 가능성이 있는 경우 |
| Info | 명확한 의도가 있는 에러, 요구사항에 따라 시스템 동작을 보여줄때 |
| Debug | Info 레벨보다 더 자세한 정보가 필요한 경우. (ex.Dev 환경) |
| Trace | Debug 레벨보다 더 자세해야 한다. Dev환경에서 버그를 해결하기 위해 사용한다. |

<br>

### (3) SLF4J (Simpe Logging Facade for Java)

![image](https://user-images.githubusercontent.com/48561660/174487934-a83f4dff-8426-4f7e-b5f7-828019bc1d68.png)

- 다양한 로깅 프레임워크에 대한 추상화(인터페이스)를 전달한다. 단독으로 사용하지 못하고 최종 사용자가 배포시 원하는 구현체를 선택한다.
- 동작 과정은 개발할 때는 SLF4J API를 사용하여 로깅 코드를 작성하고 배포할 때는 **바인딩된 로깅 프레임워크가 실제 로깅 코드를 수행하는 과정**을 거친다.
이러한 과정은 SLF4J에서 제공하는 3가지 모듈인 **Bridge, API, Binding** 모듈을 통해 수행될 수 있다.

<br>

**`Bridge`**

- SLF4J 이외의 다른 로깅 API로의 Logger 호출을 SLF4J 인터페이스로 연결하여 SLF4J API가 대신 처리할 수 있도록하는 일종의 어댑터 역할을 하는 라이브러리
- 아직 변경되지 않은 이전의 레거시 로깅 프레임워크를 위한 라이브러리
- 여러 개를 사용해도 상관없지만 Bridge와 Binding 모듈에 같은 종류의 프레임워크를 사용하면 안된다.

<br>

**`SLF4J API`**

- 로깅에 대한 추상 레이어(인터페이스) 제공(= 로깅 동작에 대한 역할을 수행할 추상메서드를 제공)
- 추상 메서드를 사용하기 때문에 단독적으로 사용할 수 없다.
- 하나의 API 모듈에 하나의 Binding 모듈을 둬야 한다.


<br>

**`Binding`**

- SLF4J API를 로깅 구현체와 연결하는 어댑터 역할을 하는 모듈
- SLF4J를 구현한 클래스에서 Binding으로 연결된 Logger의 API를 호출
- 하나의 API에 하나의 Binding을 둬야 한다.

<br>

## 2. Logback

![untitled](https://user-images.githubusercontent.com/48561660/174087503-19fdbd0d-8fdb-4295-beea-0cab5cff705b.png)

### (1) 구조

- SLF4J의 구현체이며 Log4J를 토대로 만든 프레임워크이다.
- 스프링 프레임워크는 Log4J 와 Logback을 채택하고 있다.


**`logback-core`**

- 다른 두 모듈을 위한 기반 역할을 하는 모듈
- Appender 와 Layout 인터페이스가 이 모듈에 속한다.

**`logback-classic`**

- loback-core에서 확장된 모듈 (**logback-core를 가지며 sl4j api를 구현함)
- logback-classic에 포함된 라이브러리들 해당 artifact의 올바른 버전 사용이 필요하다.
- Logger 클래스가 이 모듈에 속함.

**`logback-access`**

- ServletContext와 통합되어 HTTP 엑세스에 대한 로깅을 제공
- logback-core는 logback-access의 기반 기술이기에 필요하지만, logback-classic 및 slf4j와 무관하다.

<br>

### (2) 설정요소

**`Logger`**

- 지정된 레벨 이하의 메서드는 기록되지 않는다.
- 만약 레벨이 INFO 일 경우, WARN, ERROR 로그만 출력한다.
- 기본 레벨은 debug로 되어있다.

**`Appender`**

- Logback은 로그 이벤트를 쓰는 작업을 Appender에게 위임한다. <br>
( = 로그 메세지가 출력된 대상을 결정하는 요소)
   - ConsoleAppender : 콘솔에 출력
   - FileAppender : 파일로 출력
   - RollingFileAppender : 파일을 일정 조건에 맞게 따로 저장

**`Encoder(Layout)`**
- 로그 이벤트를 바이트 배열로 변환하고 해당 바이트 배열을 OutputStream에 쓰는 작업을 담당
- Appender에 포함되어 사용자가 지정한 형식으로 표현될 로그 메세지를 변환하는 역할을 담당하는 요소

<br>

### (3) 실습 요구사항 및 정리
1. 테스트, 개발 환경에서는 Info레벨 로그를 Console에 출력
2. 프로덕션 환경에서는 Info, Warn, Error 레벨 별 로그를 파일로 출력

**`logback-spring.xml`**
```xml
<configuration>
<!-- 3. 어떻게 출력할까?(Layout) : 출력메세지 설정 -->
<timestamp key="BY_DATE" datePattern="yyyy-MM-dd"/>
<property name="LOG_PATTERN"
          value="[%d{yyyy-MM-dd HH:mm:ss}:%-4relative] %green([%thread]) %highlight(%-5level) %boldWhite([%C.%M:%yellow(%L)]) - %msg%n"/>
  
<!-- profile: prod 아닐 경우 활성화 -->
<springProfile name="!prod">
  <include resource="console-appender.xml"/>

  <!-- 1. 어떻게 출력할까? (Logger) : INFO 레벨로 설정 -->
  <root level="INFO">
    <appender-ref ref="CONSOLE"/>
  </root>
</springProfile>

<!-- profile: prod 경우 활성화 -->
<springProfile name="prod">
  
  <include resource="file-info-appender.xml"/>
  <include resource="file-warn-appender.xml"/>
  <include resource="file-error-appender.xml"/>

  <root level="INFO">
    <appender-ref ref="FILE-INFO"/>
    <appender-ref ref="FILE-WARN"/>
    <appender-ref ref="FILE-ERROR"/>
  </root>
</springProfile>
</configuration>

```
**`file-info-appender.xml`**
```xml
<included>
    <!-- 2. 어디에 기록할까(Appender) : RollingFileAppender 사용(FileAppender를 상속하여 로그 파일을 rollover) -->
    <appender name="FILE-INFO" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>./log/info/info-${BY_DATE}.log</file>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>INFO</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
        <encoder>
            <pattern>${LOG_PATTERN}</pattern>
        </encoder>
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <fileNamePattern>./backup/info/info-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <maxFileSize>100MB</maxFileSize>
            <maxHistory>30</maxHistory>
            <totalSizeCap>3GB</totalSizeCap>
        </rollingPolicy>
    </appender>
</included>
```

### Reference

- [**[Logging] SLF4J란?**](https://livenow14.tistory.com/63)
- **[[Logging] Logback이란?](https://livenow14.tistory.com/64)**