package com.isur.backend.app.repository;

import com.isur.backend.app.model.Topic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Qualifier("topicRepo")
@Repository
public interface TopicRepository extends JpaRepository<Topic, String> {
    Optional<Topic> findByName(String name);
}