package com.cooper.serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class SerializableUtil {

    private SerializableUtil() {
    }

    /**
     * - Article 객체를 직렬화 하는 코드
     * <p>
     * @return
     */
    public static <T> String serializeMethod(T object) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        // 아래와 같은 try 구문은 java 9 버전부터 지원합니다.
        try (bos; ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(object);
        } catch (Exception e) {
            // ...Exception Handling
        }
        return Base64.getEncoder().encodeToString(bos.toByteArray());
    }

    /**
     * - byte 로 직렬화된 코드를 역직렬화 하는 코드
     * <p>
     * @param serializedString
     * @return article
     * @throws java.io.IOException
     * @throws ClassNotFoundException
     */
    public static <T> T deSerializeMethod(String serializedString) throws IOException, ClassNotFoundException {
        byte[] decodeByte = Base64.getDecoder().decode(serializedString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodeByte);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (T) objectInputStream.readObject();
    }

}
