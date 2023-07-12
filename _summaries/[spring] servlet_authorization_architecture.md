# Authorization Architecture

> [[spring.io] spring security docs 를 읽고 번역한 내용](https://docs.spring.io/spring-security/reference/5.7/servlet/authorization/architecture.html)

## Authorities

1. `GrantedAuthority`
   -  `String getAuthority()` 메서드 하나만 존재하는 인터페이스
   - `AuthenticationManager` 에 의해 인증 객체에 삽입됩니다.
   - 나중에 권한 부여 결정을 내릴 때 `AuthorizationManager` 가 읽습니다.
   - 기본 구현체로는 `SimpleGrantedAuthority` 가 있다.

<br>

## Pre-Invocation Handling

- Spring Security 는 메서드 호출이나 웹 요청과 같은 보안 객체에 대한 액세스를 제어하는 인터셉터를 제공합니다.
- 호출을 진행할 수 있는지 <u>여부에 대한 호출 전 결정은 `AccessDecisionManager` 에 의해 이루어집니다.</u>

<br>

## AuthorizationManager

`AuthorizationManager`

1. `AuthorizationManager` 는 `AccessDecisionManager` 와 `AccessDecisionVoter` 를 모두 대체합니다.
2. `AccessDecisionManager` 또는 `AccessDecisionVoter` 를 <u>사용자 정의하는 애플리케이션은 `AuthorizationManager` 를 사용하도록 
  변경하는 것이 좋습니다.</u>

3. `AuthorizationManagers`는 `AuthorizationFilter`에 의해 호출되며 최종 액세스 제어 결정을 내릴 책임이 있습니다. 
4. `AuthorizationManager` 인터페이스에는 두 가지 메서드가 있습니다.
   ```java
   AuthorizationDecision check(Supplier<Authentication> authentication, Object secureObject);
   
   default AuthorizationDecision verify(Supplier<Authentication> authentication, Object secureObject) throws AccessDeniedException {
   // ...
   }
   ```
   - `check` : 권한 부여 결정을 내리는 데 필요한 모든 관련 정보를 전달받습니다.
     - 구현은 접근이 허용되면 `AuthorizationDecision` 을 반환하고,
     - 접근이 거부되면 결정을 기권하면 `AuthorizationDecision` 로 `null` 반환할 것으로 예상됩니다.

<br>

## Delegate-based AuthorizationManager Implementations

## AuthorityAuthorizationManager

- Spring Security 와 함께 제공되는 가장 일반적인 AuthorizationManager 는 `AuthorityAuthorizationManager` 입니다.
- <u>현재 인증에서 찾을 권한 집합으로 구성됩니다.</u> 인증에 구성된 권한이 포함되어 있으면 positive AuthorizationDecision 을 반환합니다. 그렇지 않으면 음수 AuthorizationDecision을 반환합니다.

<br>

## AuthenticatedAuthorizationManager

- 해당 Provider 는 익명, 완전 인증 및 기억하기 인증 사용자를 구분하는 데 사용할 수 있습니다.
- 많은 사이트에서 remember-me 인증으로 특정 제한적 액세스를 허용하지만 전체 액세스를 위해서는 사용자가 로그인하여 신원을 확인해야 합니다.

<br>

## Adapting AccessDecisionManager and AccessDecisionVoters

- AuthorizationManager 이전에 Spring Security는 AccessDecisionManager와 AccessDecisionVoter 를 게시했습니다.
- 이전 애플리케이션을 마이그레이션하는 것과 같은 일부 경우에는 AccessDecisionManager 또는 AccessDecisionVoter를 호출하는 
  AuthorizationManager 를 도입하는 것이 바람직할 수 있습니다.

```java
@Component
public class AccessDecisionManagerAuthorizationManagerAdapter implements AuthorizationManager {
    private final AccessDecisionManager accessDecisionManager;
    private final SecurityMetadataSource securityMetadataSource;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, Object object) {
        try {
            Collection<ConfigAttributes> attributes = this.securityMetadataSource.getAttributes(object);
            this.accessDecisionManager.decide(authentication.get(), object, attributes);
            return new AuthorizationDecision(true);
        } catch (AccessDeniedException ex) {
            return new AuthorizationDecision(false);
        }
    }

    @Override
    public void verify(Supplier<Authentication> authentication, Object object) {
        Collection<ConfigAttributes> attributes = this.securityMetadataSource.getAttributes(object);
        this.accessDecisionManager.decide(authentication.get(), object, attributes);
    }
}
```

<br>

## Hierarchical Roles

- 예를 들어 ‘admin'와 'user' 역할의 개념이 있는 애플리케이션에서 관리자가 일반 사용자가 할 수 있는 모든 작업을 수행할 수 있기를 원할 수 있습니다.
  그럴 경우, <u>애플리케이션의 특정 역할이 다른 역할을 자동으로 "포함"해야 하는 경우가 발생합니다.</u>
- 역할 계층 구조를 사용하면 어떤 역할(또는 권한)에 다른 역할을 포함해야 하는지 구성할 수 있습니다. Spring Security 의 <u>RoleVoter 의 확장 버전인
  `RoleHierarchyVoter`</u>는 사용자에게 할당된 모든 “reachable authority"를 가져오는 역할 계층으로 구성됩니다.

```java
@Bean
AccessDecisionVoter hierarchyVoter() {
    RoleHierarchy hierarchy = new RoleHierarchyImpl();
    hierarchy.setHierarchy("ROLE_ADMIN > ROLE_STAFF\n" +
            "ROLE_STAFF > ROLE_USER\n" +
            "ROLE_USER > ROLE_GUEST");
    return new RoleHierarchyVoter(hierarchy);
}
```

- 계층 구조에 4개의 역할: ROLE_ADMIN >= ROLE_STAFF >= ROLE_USER >= ROLE_GUEST
- ROLE_ADMIN 으로 인증된 사용자는 위의 `RoleHierarchyVoter`를 호출하도록 조정된 `AuthorizationManager` 에
  대해 보안 제약 조건이 평가될 때 <u>네 가지 역할을 모두 가진 것처럼 동작합니다.</u>

<br>

