package com.isur.backend.app.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long dni;
    private String email;
    private String password;
    private Long mobilePhone;
    private String fullName;
    private String status;
    private String rol;
}