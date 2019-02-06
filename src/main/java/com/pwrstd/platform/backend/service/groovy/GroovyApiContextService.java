package com.pwrstd.platform.backend.service.groovy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GroovyApiContextService {

    private Map<String, String> params = new ConcurrentHashMap<>();


    public void put(String key, String value) {
        this.params.put(key, value);
    }

    public void delete(String key){
        this.params.remove(key);
    }

    public String get(String key) {
        return this.params.get(key);
    }

    public String sayMyName(){
        return params.get("name");
    }

    public void
}
