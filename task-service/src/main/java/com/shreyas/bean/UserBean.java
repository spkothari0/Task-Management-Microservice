package com.shreyas.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBean {
    private UUID id;
    @Length(min = 3, max = 20, message = "First name must be between 3 to 20 characters")
    @NotNull(message = "First name must be not null")
    private String firstName;
    @Length(min = 3, max = 20, message = "Last name must be between 3 to 20 characters")
    @NotNull(message = "Last name must be not null")
    private String lastName;

    @NotBlank(message="Email must not be null")
    @Email(message = "Invalid email address")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message="Password must not be null")
    private String password;

    @NotNull(message = "Username must not be null")
    private String username;

    @NotNull
    private String role;

    @NotNull
    private LocalDate dob;

    private Boolean isLocked = false;
    private Boolean isEnabled = true;

//    @Override
//    public String toString() {
//        return "UserBean{" +
//                "id=" + id +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                ", username='" + username + '\'' +
//                ", role='" + role + '\'' +
//                ", dob=" + dob +
//                ", isLocked=" + isLocked +
//                ", isEnabled=" + isEnabled +
//                '}';
//    }
}
