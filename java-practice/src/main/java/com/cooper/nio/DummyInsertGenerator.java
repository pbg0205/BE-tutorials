package com.cooper.nio;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class DummyInsertGenerator {

    private DummyInsertGenerator() {
    }

    public static String createDummy(Path path, String tableName, int size) throws IOException {
        Path createdFilePath = createFile(path);

        Files.write(createdFilePath, "".getBytes(), StandardOpenOption.CREATE);

        List<String> dummyData = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            String dummy = String.format("INSERT INTO employee VALUES (%d, \"사원%d\", %d);",
                    i,
                    i,
                    ((i - 1) / (size / 8)) + 1
            );

            dummyData.add(dummy);
        }

        Path appendFilePath = Files.write(
                createdFilePath,
                dummyData.stream().collect(Collectors.joining("\n")).getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.APPEND);

        return appendFilePath.toString();
    }

    private static Path createFile(Path path) throws IOException {
        return Files.createFile(path);
    }

}
