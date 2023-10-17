package com.example.springcore.spel;

import org.springframework.boot.CommandLineRunner;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @ref : https://www.baeldung.com/spring-expression-language
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SpelRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        Car car = new Car("Good manufacturer", "Model 3", 2014);
        CarPark carPark = new CarPark();
        carPark.add(car);

        StandardEvaluationContext context = new StandardEvaluationContext(carPark); // 적용 대상

        log.debug("before car = {}", car);
        ExpressionParser expressionParser = new SpelExpressionParser();
        expressionParser.parseExpression("cars[0].model").setValue(context, "Other model");

        log.debug("after car = {}", car);
        log.debug("cars[0] = {}", carPark.getCars().get(0));
    }

}
