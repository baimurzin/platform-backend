package com.pwrstd.platform.backend.service.groovy;

import com.pwrstd.platform.backend.model.Script;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.ErrorCollector;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.codehaus.groovy.control.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroovyService {

    public static final String DEFAULT_SCRIPT_NAME = "mainMethod";

    private final GroovyApiContextService api;

    @Autowired
    public GroovyService(GroovyApiContextService api) {
        this.api = api;
    }

    public Object execute(Script script) {
        Binding binding = new Binding();
        GroovyShell shell = new GroovyShell(binding);

        return shell.parse(script.getScript()).invokeMethod(script.getMethodName(), api);
    }


    public List<String> isValidScript(String script){
        try {
            new GroovyShell().parse(script);
        } catch(MultipleCompilationErrorsException cfe) {
            ErrorCollector errorCollector = cfe.getErrorCollector();
            List<String> errors = new ArrayList<>();
            ((List<Message>)errorCollector.getErrors()).forEach(err -> {
                StringWriter sw = new StringWriter();
                err.write(new PrintWriter(sw));
                errors.add(sw.toString());
            });
            return errors;
        }
        return Collections.emptyList();
    }


    public List<Method> getAllMethods(String script) {
        return Arrays.stream(new GroovyShell().parse(script)
                .getMetaClass()
                .getTheClass()
                .getDeclaredMethods())
                .filter(m -> !m.isSynthetic() && !m.getName().equals("main") && !m.getName().equals("run"))
                .collect(Collectors.toList());
    }

    public void setMethodHeader(Script script) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setTemplateLoader(new FileTemplateLoader(new File("./src/main/resources/groovy_templates")));
        Map<String, Object> root = new HashMap<>();
        root.put("script_body", script.getScript());
        root.put("main_method_name", DEFAULT_SCRIPT_NAME);
        Template temp = cfg.getTemplate("groovy_template.ftl");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(baos);
        temp.process(root, out);
        script.setScript(baos.toString());
    }
}
