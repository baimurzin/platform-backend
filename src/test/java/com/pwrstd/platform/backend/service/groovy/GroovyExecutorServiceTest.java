package com.pwrstd.platform.backend.service.groovy;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GroovyExecutorServiceTest {

    private GroovyService service;

    @Before
    public void setUp() {
        GroovyApiContextService api = new GroovyApiContextService();
        api.put("name", "Heisenberg");
        service = new GroovyService(api);

    }

    @Test
    public void executeTest() throws IOException {


        StringBuilder builder = new StringBuilder();
        Files.readAllLines(
                Paths.get(new File("/home/andrei/projects/java/platform-backend/src/test/java/com/pwrstd/platform/backend/service/groovy/test_script.groovy")
                                .getPath()
                )).forEach(builder::append);

        String script = builder.toString();

//        service.execute(script);
    }
}