package com.example.springcore.spel;

import org.springframework.boot.CommandLineRunner;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
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
        handleCarPark();
        handleCar();
    }

    private void handleCarPark() {
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

    private void handleCar() {
        Car car = new Car("Good manufacturer", "Model 1", 2014);
        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression("model");

        EvaluationContext context = new StandardEvaluationContext(car);
        String result = (String) expression.getValue(context);
        log.debug("car.make : {}", result);
    }

}
