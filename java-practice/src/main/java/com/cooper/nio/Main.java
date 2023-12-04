package com.cooper.nio;

import java.io.IOException;
import java.nio.file.Paths;

class Main {

    public static void main(String[] args) throws IOException {
        String dummyPath = DummyInsertGenerator.createDummy(
                Paths.get("/Users/cooper/Cooper/study/java/BE-tutorials/java-practice/src/main/java/com/cooper/nio/data.sql"),
                "employee",
                200);

        System.out.println("dummy data path: " + dummyPath);
    }
}
