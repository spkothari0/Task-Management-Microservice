package com.shreyas;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppConstant {
    private final Environment environment;
    public AppConstant(Environment environment) {
        this.environment = environment;
    }


    public static final String CORRELATION_ID = "Correlation-Id";
    public static final String Authorization_Header = "Authorization";
    public static final String Bearer = "Bearer";

    public Boolean IsMailServiceEnabled(){
        return Boolean.parseBoolean(environment.getProperty("MAIL_SERVICE_ENABLED"));
    }

    public String Application_Email_Address(){
        return environment.getProperty("EMAIL_ADDRESS");
    }
}
