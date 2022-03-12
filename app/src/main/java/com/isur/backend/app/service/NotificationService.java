package com.isur.backend.app.service;

import com.github.dozermapper.core.Mapper;
import com.isur.backend.app.dto.NotificationDTO;
import com.isur.backend.app.model.Notification;
import com.isur.backend.app.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    @Qualifier("notificationRepo")
    NotificationRepository repository;

    @Autowired
    FirebaseMessagingService firebaseMessagingService;

    @Autowired
    private Mapper mapper;

    public CompletableFuture<Object> createNotification(NotificationDTO notificationDTO) {
        CompletableFuture<Notification> completableFuture = CompletableFuture.supplyAsync(
                ()->{
                    StringBuffer cities = new StringBuffer();
                    StringBuffer latitudes = new StringBuffer();
                    notificationDTO.getArea().stream().forEach( noti-> {
                        cities.append(noti.getState() +","+noti.getCity()).append(";");
                        latitudes.append(noti.getLat()+","+ noti.getLon()).append(";");
                    });
                    cities.deleteCharAt(cities.length()-1);
                    latitudes.deleteCharAt(latitudes.length()-1);
                    Notification notif = new Notification();
                    notif.setTopic(notificationDTO.getTopic());
                    notif.setTitle(notificationDTO.getTitle());
                    notif.setDescription(notificationDTO.getMessage());
                    notif.setArea(cities.toString());
                    notif.setUserCreatedId(notificationDTO.getUserCreatedId());
                    notif.setDateCreated(LocalDateTime.now());
                    notif.setStatus("SENDED");
                    repository.save(notif);

                    Notification notificationFirebase = null;
                    try {
                        notificationFirebase = (Notification) notif.clone();
                        notificationFirebase.setArea(latitudes.toString());
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }


                    return notificationFirebase;
                }
        );
        CompletableFuture<Object> notified = completableFuture.thenApply(notification -> sendToNotificationService(notification));
        return notified;
    }

    public CompletableFuture<Object> sendToNotificationService(Notification notification) {
        CompletableFuture<Object> completableFuture = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        firebaseMessagingService.sendNotification(notification);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return "Voucher created";
                }
        );
        return completableFuture;
    }

    public CompletableFuture<List<Notification>> getAllNotification(Long userId) {
        CompletableFuture<List<Notification>> completableFuture = CompletableFuture.supplyAsync(
                ()->{
                    return repository.findByUserCreatedId(userId)
                            .stream()
                            .sorted(Comparator.comparing(Notification::getDateCreated).reversed())
                            .collect(Collectors.toList());
                });
        return completableFuture;
    }
}