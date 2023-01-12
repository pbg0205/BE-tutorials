package com.cooper.stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class AnyMatchTest {

    @DisplayName("anyMatch 는 요소를 포함하면 true 를 반환한다")
    @Test
    public void anyMatchTrue() {
        List<String> languages = List.of("CSE", "C++", "Java", "DS");
        boolean hasJava = languages.stream().anyMatch(language -> Objects.equals(language, "Java"));
        assertThat(hasJava).isTrue();
    }

    @DisplayName("anyMatch 는 요소를 포함하지 않으면 false 를 반환한다")
    @Test
    public void anyMatchFalse() {
        List<String> languages = List.of("CSE", "C++", "Java", "DS");
        boolean hasJavaScript = languages.stream().anyMatch(language -> Objects.equals(language, "Javascript"));
        assertThat(hasJavaScript).isFalse();
    }

}
