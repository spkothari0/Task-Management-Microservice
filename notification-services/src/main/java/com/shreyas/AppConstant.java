package com.shreyas;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppConstant {
    public static final String CORRELATION_ID = "Correlation-Id";
    public static final String Authorization_Header = "Authorization";
    public static final String Bearer = "Bearer";
}
