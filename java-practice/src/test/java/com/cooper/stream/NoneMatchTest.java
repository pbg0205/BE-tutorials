package com.cooper.stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class NoneMatchTest {

    @DisplayName("noneMatch 는 요소를 포함하지 않으면 true 를 반환한다")
    @Test
    public void noneMatchTrue() {
        List<String> languages = List.of("CSE", "C++", "Java", "DS");
        boolean hasJavaScript = languages.stream().noneMatch(language -> Objects.equals(language, "Javascript"));
        assertThat(hasJavaScript).isTrue();
    }

    @DisplayName("noneMatch 는 요소를 포함하면 false 를 반환한다")
    @Test
    public void noneMatchFalse() {
        List<String> languages = List.of("CSE", "C++", "Java", "DS");
        boolean hasJava = languages.stream().noneMatch(language -> Objects.equals(language, "Java"));
        assertThat(hasJava).isFalse();
    }

}
