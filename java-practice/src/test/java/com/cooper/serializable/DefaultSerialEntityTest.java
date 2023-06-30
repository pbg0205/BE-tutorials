package com.cooper.serializable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InvalidClassException;

import static org.assertj.core.api.Assertions.*;

class DefaultSerialEntityTest {

    @Test
    @DisplayName("serialUID 가 존재하지 않으면 클래스 정보를 통해 직렬화 한다")
    void defaultSerialize() throws IOException, ClassNotFoundException {
        //given
        DefaultSerialEntity defaultSerialEntity = new DefaultSerialEntity("이름", "pressName11", "reporterName11");
        String serializedByteStr = SerializableUtil.serializeMethod(defaultSerialEntity);

        //when
        DefaultSerialEntity deSerializedEntity = SerializableUtil.deSerializeMethod(serializedByteStr);

        //then
        assertThat(deSerializedEntity).isInstanceOf(DefaultSerialEntity.class);
    }

    @Test
    @DisplayName("DefaultSerialEntity 에 필드를 추가할 경우 다른 역직렬화 값을 반환하여 예외를 반환한다")
    void defaultSerializeWhenAddField() throws IOException, ClassNotFoundException {
        //given, when
        // phoneNumber 필드 추가 이전 직렬화 값
        String serializedByteStrBeforeFieldAdd = "rO0ABXNyACtjb20uY29vcGVyLnNlcmlhbGl6YWJsZS5EZWZhdWx0U2VyaWFsRW50aX" +
                "R54Lm3/1et2ggCAANMAAlwcmVzc05hbWV0ABJMamF2YS9sYW5nL1N0cmluZztMAAxyZXBvcnRlck5hbWVxAH4AAUwABXRpdGxlc" +
                "QB+AAF4cHQAC3ByZXNzTmFtZTExdAAOcmVwb3J0ZXJOYW1lMTF0AAbsnbTrpoQ=";

        //then
        assertThatThrownBy(() -> SerializableUtil.<DefaultSerialEntity>deSerializeMethod(serializedByteStrBeforeFieldAdd))
                .isInstanceOf(InvalidClassException.class);
    }

}
