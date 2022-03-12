package com.isur.backend.app.repository;

import com.isur.backend.app.model.Notification;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Qualifier("notificationRepo")
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserCreatedId(Long userCreatedId);

}