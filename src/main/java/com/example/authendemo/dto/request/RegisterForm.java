package com.example.authendemo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterForm {

    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
}
