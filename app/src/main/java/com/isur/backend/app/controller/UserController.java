package com.isur.backend.app.controller;

import com.isur.backend.app.dto.UserDTO;
import com.isur.backend.app.model.User;
import com.isur.backend.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("/oauth/token")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<String> login(@RequestBody UserDTO userDTO){
        return service.generateToken(userDTO);
    }

    @PostMapping("/create")
    public CompletableFuture<User> createUser(@RequestBody UserDTO userDTO) { return service.createUser(userDTO); }
}
