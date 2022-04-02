package com.isur.backend.app.controller;

import com.isur.backend.app.dto.NotificationDTO;
import com.isur.backend.app.model.Notification;
import com.isur.backend.app.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    @GetMapping("/getNotification")
    public ResponseEntity<?> getNotification(@RequestParam Map<String,String> allRequestParams) {
        List<Notification> list = service.getNotification(allRequestParams);
        return new ResponseEntity<>(list, HttpStatus.OK);

    }

}
