package com.cooper.reflection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

public class ReflectionTest {

    @Test
    @DisplayName("객체 메서드 어노테이션 값 확인 테스트")
    void methodReflectionTest() {
        Method[] methods = Person.class.getMethods(); // Object 에서 상속된 모든 메서드 반환

        Emotion basketBallEmotion = methods[0].getAnnotation(Emotion.class);
        Emotion footBallEmotion = methods[1].getAnnotation(Emotion.class);

        assertAll(
                () -> assertThat(basketBallEmotion.value()).isEqualTo("funny"),
                () -> assertThat(footBallEmotion.value()).isEqualTo("exciting")
        );
    }

    @Test
    @DisplayName("객체 메서드 어노테이션 값 확인 테스트")
    void methodParameterReflectionTest() {
        Method[] methods = Person.class.getMethods(); // Object 에서 상속된 모든 메서드 반환

        Emotion basketBallEmotion = methods[0].getAnnotation(Emotion.class);
        Emotion footBallEmotion = methods[1].getAnnotation(Emotion.class);

        assertAll(
                () -> assertThat(basketBallEmotion.value()).isEqualTo("funny"),
                () -> assertThat(footBallEmotion.value()).isEqualTo("exciting")
        );
    }

}
