package com.shreyas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private String role;
    private LocalDate dob;
    private Boolean isLocked = false;
    private Boolean isEnabled = true;
}
