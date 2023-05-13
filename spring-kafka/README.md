# spring-kafka

### Index

1. [build.gradle](#build.gradle)
2. [spring-kafka library versions](#spring-kafka-library-versions)
3. [using spring for apache kafka](#using-spring-for-apache-kafka)
   1. [connecting to kafka](#connecting-to-kafka)

## build.gradle

```groovy
dependencies {
   implementation 'org.springframework.boot:spring-boot-starter'
   implementation 'org.springframework.kafka:spring-kafka'
   testImplementation 'org.springframework.boot:spring-boot-starter-test'
   testImplementation 'org.springframework.kafka:spring-kafka-test'
}
```

- spring-boot 를 사용할 경우, 부트 버전에 호환되는 버전으로 자동 설정된다. ([spring-boot ver 2.7. 11 dependency version](https://docs.spring.io/spring-boot/docs/2.7.11/reference/html/dependency-versions.html))


## spring-kafka library versions

```
- spring-kafka:2.8.11 
  - org.apache.kafka:kafka-clients:3.1.2
  - org.springframework:retry:spring-retry:1.3.4
  - org.springframework:spring-tx:5.3.27
  - org.springframework:spring-context:5.3.27
  - org.springframework:spring-messaging:5.3.27
```

## [using spring for apache kafka](https://docs.spring.io/spring-kafka/reference/html/#kafka)

### [3.1.1.connecting to kafka](https://docs.spring.io/spring-kafka/reference/html/#connecting)

1. **`org.springframework.kafka.core` 에서 카프카 연결에 관련된 대표적인 클래스, 인터페이스**
   1. **KafkaAdmin** : 런타임 시점에 topic 을 생성하고 검사하는 메서드를 제공한다. (e.g. createOrModifyTopics, describeTopics)
   2. **ProducerFactory** : Producer 인스턴스를 생성하는 전략 인터페이스 (구현체: DefaultKafkaProducerFactory)
   3. **ConsumerFactory** : Consumer 인스턴스를 생성하기 위한 전략 인터페이스 (구현체: DefaultKafkaConsumerFactory)

   > 💡 해당 클래스 또는 구현체는  `KafkaResourceFactory` 를 상속 받는다. `KafkaResourceFactory` 의 메서드인
   > `setBootstrapServersSupplier(Supplier<String> bootstrapServersSupplier)` 를 통해 런타임 시점에 bootstrap.servers 를 번경할 수 있다.
   > <u>연결된 서버를 확인하는 용도</u>로도 활용할 수 있으니 참고하도록 하자.

   > ❓ bootstrap.servers : 카프카 클러스터를 위한 초기 연결을 수립하는 과정에서 사용하는 host/port pair 리스트이다.
   > (ref: https://kafka.apache.org/documentation/#producerconfigs_bootstrap.servers)
2. 기존의 존재하는 Producer, Consumer 를 닫는 방법
   1. DefaultKafkaProducerFactory 의 `reset()` 메서드를 호출한다.
   2. KafkaListenerEndpointRegistry 의 `stop()` 메서드를 호출하거나 다른 리스너 컨테이너 빈에서 stop() 및 start() 를 호출한다. (아직은 잘 모르겠음.🤔)
3. Listener interface 의 도입
   1. spring-kafka ver2.5 이후 버전부터, producer 또는 consumer 를 생성하거나 닫을 때마다 알림을 받기 위해 Listener 를 설정할 수 있다.
   2. [ProducerFactory](https://github.com/spring-projects/spring-kafka/blob/main/spring-kafka/src/main/java/org/springframework/kafka/core/ProducerFactory.java#L311) 와 [ConsumerFactory](https://github.com/spring-projects/spring-kafka/blob/main/spring-kafka/src/main/java/org/springframework/kafka/core/ConsumerFactory.java#L233) 의 nested interface 로 선언되어 있다.


## References

- [Spring for Apache Kafka](https://docs.spring.io/spring-kafka/reference/html/#kafka)
- [spring-kafka-api](https://docs.spring.io/spring-kafka/api/index.html)
- [apache-kafka-documentation](https://kafka.apache.org/documentation)
