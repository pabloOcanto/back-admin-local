package com.isur.backend.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserAuthenticationDTO {
    private String user;
    private String rol;
    private String tokenType ;
    private String token;
}
