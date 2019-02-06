package com.pwrstd.platform.backend.service;

import com.pwrstd.platform.backend.model.Script;
import com.pwrstd.platform.backend.service.groovy.GroovyApiContextService;
import com.pwrstd.platform.backend.service.groovy.GroovyService;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class GroovyScriptServiceTest {

    GroovyScriptService service = new GroovyScriptService(new GroovyService(new GroovyApiContextService()), null);

    @Test
    public void saveAndValidate() throws IOException, TemplateException {
        StringBuilder builder = new StringBuilder();
        Files.readAllLines(
                Paths.get(new File("/home/andrei/projects/java/platform-backend/src/test/java/com/pwrstd/platform/backend/service/groovy/test_script.groovy")
                        .getPath()
                )).forEach(line -> builder.append(line).append("\n"));

        String script = builder.toString();

        service.saveAndValidate(Script.builder()
                .script(script)
                .build());
    }
}