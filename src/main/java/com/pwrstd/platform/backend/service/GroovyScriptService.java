package com.pwrstd.platform.backend.service;


import com.pwrstd.platform.backend.model.Script;
import com.pwrstd.platform.backend.repository.ScriptRepository;
import com.pwrstd.platform.backend.service.groovy.GroovyApiContextService;
import com.pwrstd.platform.backend.service.groovy.GroovyService;
import freemarker.template.TemplateException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.pwrstd.platform.backend.service.groovy.GroovyService.DEFAULT_SCRIPT_NAME;

@Service
public class GroovyScriptService {


    private final GroovyService groovyService;
    private final ScriptRepository scriptRepository;

    @Autowired
    public GroovyScriptService(GroovyService groovyService, ScriptRepository scriptRepository) {
        this.groovyService = groovyService;
        this.scriptRepository = scriptRepository;
    }

    public Script saveAndValidate(Script script) throws IOException, TemplateException {
        List<String> validationErrors = normalize(script);
        if (!validationErrors.isEmpty()) {
            throw new ValidationException(String.join("\n", validationErrors));
        }
        return scriptRepository.save(script);
    }

    @NotNull
    private List<String> normalize(Script script) throws IOException, TemplateException {
        List<String> errors = new ArrayList<>();
        if(script.getScript() == null) {
            return Collections.emptyList();
        }

        errors.addAll(groovyService.isValidScript(script.getScript()));
        if(errors.size() > 0) {
            return errors;
        }
        List<Method> allMethods = groovyService.getAllMethods(script.getScript());
        if (allMethods.size() == 0) {
            if(script.getScript().length() > 0) {
                groovyService.setMethodHeader(script);
                script.setMethodName(DEFAULT_SCRIPT_NAME);
            } else {
                errors.add("Groovy should have any logic in script");
            }
        } else {
            Optional<Method> first = allMethods.stream()
                    .filter(sc -> {
                        Class<?>[] parameterTypes = sc.getParameterTypes();
                        if(parameterTypes.length > 1) {
                            return false;
                        }
                        return parameterTypes[0].getName().equals(GroovyApiContextService.class.getName());
                    }).findFirst();
            if(first.isPresent()) {
                script.setMethodName(first.get().getName());
            } else {
                errors.add("Groovy script should have method with only one parameter " +
                        "'com.pwrstd.platform.backend.service.groovy.GroovyApiContextService'");

            }
        }
        errors.addAll(groovyService.isValidScript(script.getScript()));//double check
        return errors;
    }
}
