package com.shreyas.filter.correlation;

import com.shreyas.AppConstant;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CorrelationIdInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException{
        String correlationId = MDC.get(AppConstant.CORRELATION_ID);
        if(correlationId!=null){
            request.getHeaders().add(AppConstant.CORRELATION_ID, correlationId);
        }
        return execution.execute(request, body);
    }
}
