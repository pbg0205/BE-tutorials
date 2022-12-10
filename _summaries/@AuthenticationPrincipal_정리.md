> 줌인터넷에 신입 개발자의 파일럿 프로젝트에 관한 블로그 글이 있었다.
> 짧은 기간 작업을 하지만 스프링 공식 문서를 통해서 문제를 해결해나가는 과정이 인상 깊었다.  
> ***@AuthenticationPrincipal*** 과 @CreatedBy, @Modified를 통해서 인증 객체를 주입한 내용이 좋아서 정리하려고 한다 😃

## ***@AuthenticationPrincipal***

---

### 1.  **@AuthenticationPrincipal??**

[[spring.io]
@AuthenticationPrincipal](https://docs.spring.io/spring-security/reference/servlet/integrations/mvc.html#mvc-authentication-principal)
> @AuthenticationPrincipal [[spring docs](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/annotation/AuthenticationPrincipal.html)] :
> Annotation that is used to
> resolve [Authentication.getPrincipal()](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html#getPrincipal())
> to a method argument.

- **Authentication.getPrincipal**
  을 [Resolver](https://www.notion.so/zum-tech-CMS-A-3e8e9fdcfa5a4fe1a7562ec71affd2dd)를 거쳐 **method argument** 로 데이터를
  주입한다.
- **@AuthenticationPrincipal** 을 선언하면 **AuthenticationPrincipalArgumentResolver**을 통해 자동으로 Prinicipal 의 하위 객체를 주입해준다.

`예시`

```java
@PutMapping
public ResponseEntity updateComment(@RequestBody @Valid CommentUpdateRequestDto dto,
@AuthenticationPrincipal Account account){

        CommentResponseDto responseDto=commentService.updateComment(dto,account);
        return ResponseEntity.ok(responseDto);

        }
```

- 만약 @AuthenticationPrincipal 어노테이션 없이 Principal 을 사용하면 인증하고자 하는 사용자 입력 정보를 받아오기 때문에 주의하자!!!

**Resolver**

- 파라미터 지정 변수들을 annotation 이나 type 에 데이터를 주입하는 역할을 담당하는 컴포넌트. 대표적인 예로 **HandlerMethodArgumentResolver** 가
  있다. `Controller`의 `Argument(Parameter)`에 지정된 변수들을 Resolver 를 거쳐 실제 데이터를 주입시켜 준다.

참고 : ****[[Spring] Resolver 란? Resolver 구현하기(HandlerMethodArgumentResolver)](https://velog.io/@gillog/Spring-HandlerMethodArgumentResolver-PathVariable-RequestHeader-RequestParam)****

---

### 2. **@AuthenticationPrincipal 기반한 custom annotation**

- **@AuthenticationPrincipal** 을 기반한 커스텀 어노테이션을 만들 수도 있다.
- custom annotation 을 사용하면 동일하게 **AuthenticationPrincipalArgumentResolver** 가 커트롤러 method Argument를 전달한다.

<br>

> [parameter & argument 차이](http://taewan.kim/tip/argument_parameter/)

- **parameter** : 메서드에 선언한 입력 변수명
- **argument** :  선언한 메서드를 사용할 때의 입력 값 (왜 갑자기 헷갈리지…?)

- 커스텀 어노테이션을 사용하면 의미를 명확히 전달할 수 있어 코드의 가독성을 높혀주는 장점이 있다.

**`예시`**

1. **커스텀 어노테이션**

    ```java
    @Target({ ElementType.PARAMETER })
    @Retention(RetentionPolicy.RUNTIME)
    @AuthenticationPrincipal
    public @interface CurrentUser {
    }
    ```

2. 컨트롤러
    ```java
    @Controller
    public class MyController {
       @RequestMapping("/user/current/show")
       public String show(@CurrentUser CustomUser customUser) {
           // do something with CustomUser
           return "view";
       }
    }
    ```

- **
  References : [[spring docs] AuthenticationPrincipalArgumentResolver](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/bind/support/AuthenticationPrincipalArgumentResolver.html)**

---

### 3. **AuthenticationPrincipalArgumentResolver??**

- AuthenticationPrincipalArgumentResolver

> [[spring docs](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/method/annotation/AuthenticationPrincipalArgumentResolver.html)]
> Allows resolving the Authentication.getPrincipal() using the AuthenticationPrincipal annotation

-
package [org.springframework.security.web.method.annotation](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/method/annotation/package-summary.html)
하위에 존재하는 클래스

(
package [org.springframework.security.web.bind.support](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/bind/support/package-summary.html)
에 있는 클래스는 deprecated)

- spring 5.0 이상은 충분히 사용 가능하다. (spring 4.0 부터 도입되었음)

---

### 4. **AuthenticationPrincipalArgumentResolver 의 대표적인 메서드 살펴보기**

> **AuthenticationPrincipalArgumentResolver 의 대표적인 메서드** `supportsParameter` 와 `resolveArgument` **를 확인해보자.**
>

`supportsParameter`

```java
public final class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    // do something...

    public boolean supportsParameter(MethodParameter parameter) {
        return this.findMethodAnnotation(AuthenticationPrincipal.class, parameter) != null;
    }

    private <T extends Annotation> T findMethodAnnotation(Class<T> annotationClass, MethodParameter parameter) {
        T annotation = parameter.getParameterAnnotation(annotationClass);
        if (annotation != null) {
            return annotation;
        } else {
            Annotation[] annotationsToSearch = parameter.getParameterAnnotations();
            Annotation[] var5 = annotationsToSearch;
            int var6 = annotationsToSearch.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                Annotation toSearch = var5[var7];
                annotation = AnnotationUtils.findAnnotation(toSearch.annotationType(), annotationClass);
                if (annotation != null) {
                    return annotation;
                }
            }

            return null;
        }
    }
```

1. 외부에서 MethodParameter의 annotaion 목록을 확인한다.

   (아마 메서드의 각 파라미터를 탐색하면서 확인하는 것 같다.)

2. AnnotationUtils를 통해 어노테이션이 **AuthenticationPrincipal 타입**인지를 찾는다.
3. 만약에 어노테이션 타입이 존재하면 해당 annotaion 을 반환하고 그렇지 않으면 null을 반환한다.

`resolveArgument`

```java
public Object resolveArgument(MethodParameter parameter,ModelAndViewContainer mavContainer,NativeWebRequest webRequest,WebDataBinderFactory binderFactory){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null){
        return null;
        }else{
        Object principal=authentication.getPrincipal();
        AuthenticationPrincipal annotation=(AuthenticationPrincipal)this.findMethodAnnotation(AuthenticationPrincipal.class,parameter); //(1)
        String expressionToParse=annotation.expression();
        if(StringUtils.hasLength(expressionToParse)){
        StandardEvaluationContext context=new StandardEvaluationContext(); // (2)
        context.setRootObject(principal);
        context.setVariable("this",principal);
        context.setBeanResolver(this.beanResolver);
        Expression expression=this.parser.parseExpression(expressionToParse); // (3)
        principal=expression.getValue(context);
        }

        if(principal!=null&&!ClassUtils.isAssignable(parameter.getParameterType(),principal.getClass())){
        if(annotation.errorOnInvalidType()){
        throw new ClassCastException(principal+" is not assignable to "+parameter.getParameterType());
        }else{
        return null;
        }
        }else{
        return principal;
        }
        }
        }
```

1. 해당 메서드의 파라미터에서 @AuthenticationPrincipal 어노테이션이 있는지를 탐색한다.
2. *StandardEvaluationContext* 클래스는 해당 표현식(e.g. spEL) 을 평가할 개체를 지정한다. (by. reflection)
3. Expression 객체는 ExpressionParser 을 통해 표현식 문자열 구문 분석을 한 내용과 *StandardEvaluationContext* 에 평가할 개체를 토대로 원하는 객체를 호출한다.

<br>

> ***만약 custom HandlerMethodArgumentResolver 를 추가 싶다면?***
>

1. HandlerMethodArgumentResolver 를 작성한다.
2. **`WebMvcConfigurer`** interface 구현체에서 **`addArgumentResolvers`** 메서드를 통해 추가한다.

```java

@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(fooBarHandlerMethodArgumentResolver());
    }

}
```

---

### References

- [baeldung] Spring Expression Language Guide] : https://www.baeldung.com/spring-expression-language
- [Zoom Tech blog] 게시판 CMS - 파일럿 프로젝트 : (https://zuminternet.github.io/ZUM-PILOT-WONOH/)
