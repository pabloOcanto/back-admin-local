package com.isur.backend.app.controller;

import com.isur.backend.app.dto.NotificationDTO;
import com.isur.backend.app.model.Notification;
import com.isur.backend.app.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/v1/notification")
public class NotificationController {

    @Autowired
    NotificationService service;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<Object> createNotification(@RequestBody NotificationDTO notificationDTO) {
        return service.createNotification(notificationDTO);
    }

    @GetMapping("/getAllNotification")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<List<Notification>>  getAllNotification(@RequestParam Long userId){
        return service.getAllNotification(userId);
    }
}
