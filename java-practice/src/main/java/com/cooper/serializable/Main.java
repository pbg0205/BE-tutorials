package com.cooper.serializable;

import java.io.IOException;

class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DefaultSerialEntity defaultSerialEntity = new DefaultSerialEntity("직렬화는 무엇인가", "김탱일보", "김탱");
        String serializedString = SerializableUtil.serializeMethod(defaultSerialEntity);
        System.out.println(serializedString);

        DefaultSerialEntity deSerializedDefaultSerialEntity = SerializableUtil.deSerializeMethod("rO0ABXNyAB9jb20uY29vcGVyLnNlcmlhbGl6YWJsZS5BcnRpY2xlcisKC0BkGOYCAARMAAtwaG9uZU51bWJlcnQAEkxqYXZhL2xhbmcvU3RyaW5nO0wACXByZXNzTmFtZXEAfgABTAAMcmVwb3J0ZXJOYW1lcQB+AAFMAAV0aXRsZXEAfgABeHBwdAAM6rmA7YOx7J2867O0dAAG6rmA7YOxdAAZ7KeB66Cs7ZmU64qUIOustOyXh+yduOqwgA==");
        System.out.println("article = " + deSerializedDefaultSerialEntity);
    }

}
