package com.shreyas.service.interfaces;

import com.shreyas.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE", configuration = FeignClientConfig.class)
public interface UserService {
    @GetMapping("/api/v1/user/")
    Object getUser(@RequestHeader("Authorization") String token);
}
