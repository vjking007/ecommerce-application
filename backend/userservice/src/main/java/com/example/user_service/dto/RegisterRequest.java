package com.example.user_service.dto;

import com.example.user_service.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank
    private String username;

    @Email
    private String email;

    @Size(min = 6)
    private String password;

    @NotNull(message = "Role is mandatory")
    private Role role;

}
