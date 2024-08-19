package com.shreyas.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {
    @Bean
    public RequestInterceptor feignCorrelationIdInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                String CORRELATION_ID_HEADER_NAME = "Correlation-Id";
                // Add your logic to get the Correlation-Id, e.g., from a ThreadLocal, or generate it.
                String correlationId = MDC.get(CORRELATION_ID_HEADER_NAME); // Replace with your logic to retrieve the ID

                template.header(CORRELATION_ID_HEADER_NAME, correlationId);
            }
        };
    }
}
