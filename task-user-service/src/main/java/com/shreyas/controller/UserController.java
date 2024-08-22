package com.shreyas.controller;

import com.shreyas.bean.UserBean;
import com.shreyas.entity.Constants.RoleType;
import com.shreyas.service.interfaces.IUserServices;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class UserController extends BaseController {
    private final IUserServices userServices;

    public UserController(IUserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping(value = "/register")
    @Operation(summary = "Register a user with the specified username and password and other information. Accessible to Admin only.",
            description = "The Role of a user can be one of the following: ROLE_ADMIN, ROLE_STUDENT, ROLE_TEACHER, ROLE_USER."
    )
    public ResponseEntity<APIResponse<UserBean>> register(@Valid @RequestBody UserBean user) {
        List<RoleType> roleList = new ArrayList<>(Arrays.asList(RoleType.values()));
        String role;
        try {
            role = RoleType.valueOf(user.getRole()).toString();
            user.setRole(role);
        } catch (IllegalArgumentException e) {
            return ErrorResponse("Invalid role. Role must be one of: " + roleList);
        }

        UserBean createdUser = userServices.createUser(user);
        return SuccessResponse("User registered successfully", createdUser);
    }

    @GetMapping(value ="/")
    public ResponseEntity<APIResponse<UserBean>> getUser(@RequestHeader("Authorization") String token){
        UserBean user = userServices.getUser(token);
        return SuccessResponse("User fetched successfully", user);
    }

    @GetMapping(value ="/all")
    public ResponseEntity<APIResponse<List<UserBean>>> getAllUsers(){
        List<UserBean> users = userServices.getAllUsers();
        return SuccessResponse("Users fetched successfully", users);
    }

//    @GetMapping(value ="/verification/{token}", produces = MediaType.ALL_VALUE)
//    @Operation(summary = "Verify that user is registered and unlocked.",
//            description = "Should be accessed only via link from the email address.")
//    public ResponseEntity<String> verifyUser(@PathVariable String token) {
//        String response = userServices.verifyUser(token);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

//    @PostMapping(value = "/uploadProfileImage", produces = MediaType.ALL_VALUE)
//    @Operation(summary = "Upload profile image for the user.",
//            description = "Upload profile image for the user.")
//    public ResponseEntity<APIResponse<Void>> uploadProfileImage(@RequestParam("file")MultipartFile file){
//        UserDetails user= (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        boolean response = userServices.uploadProfileImage(user.getUsername(),file);
//        if(response)
//            return SuccessResponseMessage("Profile image uploaded successfully!");
//        else
//            return ErrorResponse("Profile image upload failed!");
//    }
}
