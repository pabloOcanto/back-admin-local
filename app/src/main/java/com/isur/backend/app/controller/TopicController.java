package com.isur.backend.app.controller;

import com.isur.backend.app.dto.TopicDTO;
import com.isur.backend.app.model.Topic;
import com.isur.backend.app.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/v1/topic")
public class TopicController {

    @Autowired
    TopicService topicService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<Topic> createCity(@RequestBody TopicDTO topicDTO) {
        return topicService.createTopic(topicDTO);
    }

    @GetMapping("/getAllTopic")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<List<TopicDTO>>  getAll(){
        return topicService.getAll();
    }
}
