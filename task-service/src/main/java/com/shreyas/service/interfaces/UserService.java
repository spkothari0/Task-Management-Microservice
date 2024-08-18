package com.shreyas.service.interfaces;

import com.shreyas.bean.UserBean;
import com.shreyas.config.FeignClientConfig;
import com.shreyas.controller.APIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE", url = "http://localhost:5001", configuration = FeignClientConfig.class)
public interface UserService {
    @GetMapping("/api/v1/user/")
    public Object getUser(@RequestHeader("Authorization") String token);
}
