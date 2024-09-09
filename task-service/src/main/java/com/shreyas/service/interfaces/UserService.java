package com.shreyas.service.interfaces;

import com.shreyas.config.FeignClientConfig;
import com.shreyas.controller.APIResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE", configuration = FeignClientConfig.class)
public interface UserService {
    @GetMapping("/api/v1/user/")
    @CircuitBreaker(name = "user", fallbackMethod = "getUserFallback")
    Object getUser(@RequestHeader("Authorization") String token);

    default Object getUserFallback(String token, Throwable t) {
        return new APIResponse<Void>("ERROR", null, "User Service unavailable at the moment. Please try later", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
