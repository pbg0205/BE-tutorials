package com.cooper.reflection;

public class Person {

    private String name;

    public Person(String name) {
        this.name = name;
    }

    @Emotion("funny")
    public void actBasketball(String friendName) {
        System.out.printf("%s와 재밌게 농구를 합니다", friendName);
    }

    @Emotion("exciting")
    public void actFootBall(String friendName) {
        System.out.printf("%s와 재밌게 축구를 합니다", friendName);
    }

}
