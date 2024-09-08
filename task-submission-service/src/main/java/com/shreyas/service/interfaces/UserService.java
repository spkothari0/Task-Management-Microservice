package com.shreyas.service.interfaces;

import com.shreyas.bean.TaskDto;
import com.shreyas.bean.UserDto;
import com.shreyas.config.FeignClientConfig;
import com.shreyas.controller.APIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@FeignClient(name = "USER-SERVICE", configuration = FeignClientConfig.class)
public interface UserService {
    @GetMapping("/api/v1/user/")
    APIResponse<UserDto> getUser(@RequestHeader("Authorization") String token);
}
