# hibernate-validator-practice

## [1] hibernate-validator ?

`이전의 유효성 검사의 단점들`
- 유효성 로직이 애플리케이션 전체에 분산되어 있다.
- 코드 중복이 심하다.
- 비즈니스 로직에 섞여있어 검사 로직 추척이 어렵고 애플리케이션이 복잡해진다.

<br>

`hibernate-validator` 의 장점
- 입력값 검증 실패에 대해 원인을 쉽게 파악하고 이해하기 쉽다.
- 특정 애플리케이션 계층이나 프로그래밍 모델에 연결되지 않는 장점이 있다. (낮은 의존성)

<br>

## [2] hibernate-validator dependency
`gradle`
```groovy
dependencies {
    //...
    implementation 'org.hibernate.validator:hibernate-validator'
    //...
}
```

<br>

## [3] custom validator

### (1) custom annotation

```java
@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = LongitudeValidator.class) // 유효성 규칙을 선언할 validator 선언
@Documented
public @interface Longitude {
    
    String message() default "Invalid Longitude";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
}

```

<br>

### (2) CustomValidator
```java
import com.example.hibernatevalidatorpractice.annotation.Latitude;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LatitudeValidator implements ConstraintValidator<Latitude, Double> {

    private static final double MAX_LATITUDE_RANGE = 90.0;
    private static final double MIN_LATITUDE_RANGE = -90.0;

    @Override
    public void initialize(Latitude constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (ObjectUtils.isEmpty(value)) {
            return false;
        }

        if((value < MIN_LATITUDE_RANGE)  || (value > MAX_LATITUDE_RANGE)) {
            return false;
        }

        return true;
    }

}
```

<br>

### (3) LocationController
```java
@RestController
class ValidateRequestBodyController {

  @PostMapping("/validateBody")
  ResponseEntity<String> validateBody(@Valid @RequestBody Input input) {
    return ResponseEntity.ok("valid");
  }

}
```
- `@RequestBody` 에 유효성을 검사하는 경우에는 `@Valid` 어노테이션만 선언하면 된다.
   - 유효성 검사가 실패할 경우, 기본적으로 `MethodArgumentNotValidException` 예외를 반환하며 400 에러코드를 전달한다.

<br>

```java
@RestController
@Validated
class ValidateParametersController {

  @GetMapping("/validatePathVariable/{id}")
  ResponseEntity<String> validatePathVariable(
      @PathVariable("id") @Min(5) int id) {
    return ResponseEntity.ok("valid");
  }
  
  @GetMapping("/validateRequestParameter")
  ResponseEntity<String> validateRequestParameter(
      @RequestParam("param") @Min(5) int param) { 
    return ResponseEntity.ok("valid");
  }
}
```
- `@RequestParam`, `@PathVariable` validation 을 하기 위해서는 Controller 상위에 `@Validated` 를 선언한다.
- 기본 예외 핸들러를 등록하지 않으므로 기본적으로 HTTP 상태 **500(내부 서버 오류)** 으로 응답을 발생시킨다.

<br>

## References

- [reflectoring.io] Validation with Spring Boot - the Complete Guide : https://reflectoring.io/bean-validation-with-spring-boot/
- [hibernate docs] Hibernate Validator 8.0.0.Final - Jakarta Bean Validation Reference Implementation: Reference Guide6. Creating custom constraints : https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#validator-customconstraints 
- [hibernate docs] Hibernate Validator 8.0.0.Final - Jakarta Bean Validation Reference Implementation : https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#chapter-bean-constraints 
- [NHN cloud] Validation 어디까지 해봤니? : https://meetup.toast.com/posts/223
