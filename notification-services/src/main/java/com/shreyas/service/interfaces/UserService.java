package com.shreyas.service.interfaces;

import com.shreyas.AppConstant;
import com.shreyas.controller.APIResponse;
import com.shreyas.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@Service
@FeignClient(name = "USER-SERVICE")
public interface UserService {
    @GetMapping("/api/v1/user/{id}")
    APIResponse<UserDto> getUser(@PathVariable("id") UUID id, @RequestHeader(AppConstant.CORRELATION_ID) String correlationId,
                                 @RequestHeader(AppConstant.Authorization_Header) String token);
}
