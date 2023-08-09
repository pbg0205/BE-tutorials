# Hibernate Multitenancy

## Index

- Multitenancy introduction
    - Multitenancy
    - 다양한 클라이언트(tenant) 데이터 격리 방법
- Multitenancy interface
    - MultiTenantConnectionProvider
    - CurrentTenantIdentifierResolver

## Multitenancy introduction


### Multitenancy

- 일반적으로 멀티테넌시라는 용어는 소프트웨어 개발에 적용되어 실행 중인 **애플리케이션의 단일 인스턴스가 동시에 여러 클라이언트(tenant)에 서비스를 제공하는 아키텍처**를 의미한다.
- 이러한 시스템에서는 **다양한 테넌트와 관련된 정보(데이터, 사용자 지정 등)를 격리하는 것**이 과제이다.

### 다양한 클라이언트(tenant) 데이터 격리 방법

- **separate database**
    - **물리적으로 DB instance 를 분리**하여 고객사의 데이터를 관리
    - JDBC Connection 은 각 DB 를 가리키므로 모든 풀링은 테넌트 별로 동작한다.
    - 테넌트 별로 **JDBC Connection Pool 을 정의**하고 테넌트 식별자를 기반으로 사용할 풀을 선택한다.
- **separate schema**
    - **하나의 db instance 에 관리하고 schema 만 분리**하여 관리
    - 드라이버가 **연결 URL에서 기본 스키마 이름을 지정하는 것을 지원**하거나 **풀링 메커니즘이 연결에 사용할 스키마 이름을 지정하는 것을 지원하는 경우에 JDBC Connection Pool 을** 갖게 된다.
    - **SET SCHEMA** 명령을 통한 Schema 변경하는 경우, 단일 JDBC Connection Pool 을 갖게 되며 테넌트 식별자로 명명된 스키마를 참조하도록 변경한다.
- **partitioned (discriminator) data**
    - 모든 데이터는 단일 데이터베이스 스키마에 보관한다. 각 테넌트의 데이터를 파티션 값 또는 식별자를 사용하여 분할한다.
    - 이 접근 방식은 단일 Connection Pool 을 사용하여 모든 테넌트의 서비스를 제공한다.

## Multitenancy interface

- **MultiTenantConnectionProvider -** 구체적인 테넌트 식별자에 대한 데이터베이스 연결을 제공
- **CurrentTenantIdentifierResolver -** 사용할 테넌트 식별하는 확인

<br>

### MultiTenantConnectionProvider

- **구체적인 테넌트 식별자에 대한 데이터베이스 연결을 제공**하는 인터페이스
- Hibernate 에서 사용할 테넌트 식별자를 확인할 수 없는 경우 **getAnyConnection()** 메서드를 사용하여 연결을 가져오고 그렇지 않은 경우 **getConnection()** 메서드를 사용한다.
- **hibernate.tenant_identifier_resolver** 로 지정 필요
- 두가지 구현 방법
    - Java DataSource interface사용 - DataSourceBasedMultiTenantConnectionProviderImpl 구현을 사용한다.
    - ConnectionProvider interface 사용 - **AbstractMultiTenantConnectionProvider** 구현을 사용.

```java
package org.hibernate.engine.jdbc.connections.spi;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.service.Service;
import org.hibernate.service.spi.Wrapped;

public interface MultiTenantConnectionProvider extends Service, Wrapped {

	public Connection getAnyConnection() throws SQLException;

	public void releaseAnyConnection(Connection connection) throws SQLException;

}
```

<br>

### **CurrentTenantIdentifierResolver**

- **구체적인 테넌트 식별자을 확인**하기 위한 인터페이스 이다.
    - **resolveCurrentTenantIdentifier()**
        - 최근 테넌트 식별자를 호출.
    - **validateExistingCurrentSessions()**
        - Hibernate 가 모든 기존 세션이 동일한 식별자에 속하는지 여부를 확인하는 메서드. **true 일 경우, resolveCurrentTenantIdentifier() 을 호출**한다.
- **hibernate.multi_tenant_connection_provider** 프로퍼티로 지정 필요

```java
package org.hibernate.context.spi;

public interface CurrentTenantIdentifierResolver {

    String resolveCurrentTenantIdentifier();

    boolean validateExistingCurrentSessions();
}
```

<br>

## 구현  시, 고려 사항

### **DataSource 관리**

**커넥션을 획득 방법을 추상화 하는 인터페이스**인 DataSource 는 생성하는 비용이 크다.
**모든 DataSource 를 관리하는 것이 비효율적**이므로 **캐싱(Cache)에 관한 내용도 고민**해보자.

### OSIV(Open Session In View)

**OSIV 를 활성화할 경우, 요청 시작되면서 EntityManager 를 생성하며 도중에 EntityManager 를 변경할 수 없다.**
비즈니스 로직에 따라 Tenant 를 식별할 수 없는 경우가 있으므로 요청 테넌트를 특정할 수 없는 경우를 대비해 OSIV 를 비활성화 해야 한다.

### 도메인 분리

물리적 DB 를 분리하는 경우에는 **외래키 제약사항 및 join 이 불가능하다.**
그러므로 JPA 를 사용하는 경우, 연관 관계를 제거하고 반정규화를 해야 한다.
또한 운영 중인 서비스의 경우에는 배포 시에 **데이터 마이그레이션에 관한 작업**도 필요하다.

<br>

## References

- [[Hibernate] Multitenancy](https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#multitenacy)
- [[baeldung] A Guide to Multitenancy in Hibernate 6](https://www.baeldung.com/hibernate-6-multitenancy)
