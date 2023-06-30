package com.cooper.serializable;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class SerialUIDEntityTest {

    @Test
    @DisplayName("serialVersionUID 을 선언하면 클래스 정보 관계없이 serialVersionUID 기준으로 직렬화된다.")
    void SerialVersionUID() throws IOException, ClassNotFoundException {
        //given, when
        // phoneNumber 필드 추가 이전 직렬화 값 : new SerialUIDEntity("title222", "pressName222", "reporter222");
        String serializedByteStrBeforeFieldAdd = "rO0ABXNyACdjb20uY29vcGVyLnNlcmlhbGl6YWJsZS5TZXJpYWxVSURFbnRpdHmdPCX" +
                "tUpN9UQIAA0wACXByZXNzTmFtZXQAEkxqYXZhL2xhbmcvU3RyaW5nO0wADHJlcG9ydGVyTmFtZXEAfgABTAAFdGl0bGVxAH4AAXh" +
                "wdAAMcHJlc3NOYW1lMjIydAALcmVwb3J0ZXIyMjJ0AAh0aXRsZTIyMg==";

        //then
        Assertions.assertThat(SerializableUtil.<SerialUIDEntity>deSerializeMethod(serializedByteStrBeforeFieldAdd))
                .isInstanceOf(SerialUIDEntity.class);
    }

}
