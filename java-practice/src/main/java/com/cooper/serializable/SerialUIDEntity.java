package com.cooper.serializable;

import java.io.Serial;
import java.io.Serializable;

public class SerialUIDEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -7116771609929351855L;

    private String title;
    private String pressName;
    private String reporterName;

    // 새로운 멤버 추가
    private String phoneNumber;

    public SerialUIDEntity(String title, String pressName, String reporterName) {
        this.title = title;
        this.pressName = pressName;
        this.reporterName = reporterName;
    }

    @Override
    public String toString() {
        return "SerialUIDEntity{" +
                "title='" + title + '\'' +
                ", pressName='" + pressName + '\'' +
                ", reporterName='" + reporterName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

}
