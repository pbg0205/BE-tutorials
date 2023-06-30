package com.cooper.serializable;

import java.io.Serializable;

class DefaultSerialEntity implements Serializable {

    private String title;
    private String pressName;
    private String reporterName;

    // 새로운 멤버 추가
    private String phoneNumber;

    public DefaultSerialEntity(String title, String pressName, String reporterName) {
        this.title = title;
        this.pressName = pressName;
        this.reporterName = reporterName;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", pressName='" + pressName + '\'' +
                ", reporterName='" + reporterName + '\'' +
                '}';
    }
}

