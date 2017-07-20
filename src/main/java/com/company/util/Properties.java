package com.company.util;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class Properties implements EnvironmentAware {
    public static Environment environment;

    public static String getProperty(String key) {
        return environment.getProperty(key);
    }

    @Override
    public void setEnvironment(Environment env) {
        if(env != null) {
            environment = env;
        }
    }
}
