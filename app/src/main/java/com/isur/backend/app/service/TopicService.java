package com.isur.backend.app.service;

import com.github.dozermapper.core.Mapper;
import com.isur.backend.app.dto.TopicDTO;
import com.isur.backend.app.exception.CreatedTopicException;
import com.isur.backend.app.model.Topic;
import com.isur.backend.app.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    @Qualifier("topicRepo")
    TopicRepository repository;

    @Autowired
    private Mapper mapper;

    public CompletableFuture<Topic> createTopic(TopicDTO topicDTO) {
        CompletableFuture<Topic> completableFuture = CompletableFuture.supplyAsync(
                ()->{
                    Optional<Topic> topicOpt = repository.findByName(topicDTO.getName());
                    if (topicOpt.isPresent()) throw new CreatedTopicException();
                    Topic topic = new Topic();
                    mapper.map(topicDTO,topic);
                    return repository.save(topic);
                }
        );
        return completableFuture;
    }

    public CompletableFuture<List<TopicDTO>> getAll(){
        CompletableFuture<List<TopicDTO>> completableFuture = CompletableFuture.supplyAsync(
                ()->{
                    return
                            repository
                                    .findAll()
                                    .stream()
                                    .filter( topic -> topic.getStatus().equals("ENABLED"))
                                    .map((topic)->{
                                       TopicDTO topicDTO= new TopicDTO();
                                        mapper.map(topic,topicDTO);
                                        return topicDTO;
                                    })
                                    .collect(Collectors.toList());
                });

        return completableFuture;
    }
}
