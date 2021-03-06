package com.isur.backend.app.service;

import com.github.dozermapper.core.Mapper;
import com.isur.backend.app.dto.NotificationDTO;
import com.isur.backend.app.dto.NotificationFCMDTO;
import com.isur.backend.app.model.Notification;
import com.isur.backend.app.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

    @PersistenceContext
    private EntityManager em;

    public CompletableFuture<Object> createNotification(NotificationDTO notificationDTO) {
        CompletableFuture<Notification> completableFuture = CompletableFuture.supplyAsync(
                () -> {
                    StringBuffer cities = new StringBuffer();
                    StringBuffer latitudes = new StringBuffer();
                    notificationDTO.getArea().stream().forEach(noti -> {
                        cities.append(noti.getState() + ";" + noti.getCity()).append("_");
                        latitudes.append(noti.getLat() + ";" + noti.getLon()).append("_");
                    });
                    cities.deleteCharAt(cities.length() - 1);
                    latitudes.deleteCharAt(latitudes.length() - 1);

                    Notification notif = new Notification();
                    notif.setTopic(notificationDTO.getTopic());
                    notif.setTitle(notificationDTO.getTitle());
                    notif.setMessage(notificationDTO.getMessage());
                    notif.setLocationdescription(notificationDTO.getLocationdescription());
                    notif.setIcon(notificationDTO.getIcon());
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

    //TODO Implementar para IOS, esta solo envia a Android.
    public CompletableFuture<Object> sendToNotificationService(Notification notification) {
        CompletableFuture<Object> completableFuture = CompletableFuture.supplyAsync(
                () -> {
                    try {

                        HashMap notificationHeader = new HashMap();
                        HashMap notificationData = new HashMap();

                        notificationHeader.put("title", notification.getTitle());
                        notificationHeader.put("sound", "Tri-tone");

                        NotificationFCMDTO notificationFCMDTO = new NotificationFCMDTO();
                        notificationFCMDTO.setTo("/topics/" + notification.getTopic());
                        notificationFCMDTO.setNotification(notificationHeader);

                        notificationData.put("topic", notification.getTopic());
                        notificationData.put("title", notification.getTitle());
                        notificationData.put("message", notification.getMessage());
                        notificationData.put("locationdescription", notification.getLocationdescription());//TODO sacar harcodeo
                        notificationData.put("icon", notification.getIcon());
                        notificationData.put("location", notification.getArea());
                        notificationFCMDTO.setData(notificationData);
                        // notificationFCMDTO.setContentAvaible(true);
                        firebaseMessagingService.sendNotification(notificationFCMDTO);
                        //firebaseMessagingService.sendNotificationToIos(notificationFCMDTO);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return "Notification created";
                }
        );
        return completableFuture;
    }

    public PageImpl<Notification> getNotification(Map<String, String> allRequestParams) {
        //.sorted(Comparator.comparing(Notification::getDateCreated).reversed())

        if (allRequestParams.isEmpty()) {

            return new PageImpl<Notification>(
                    repository
                            .findAll()
                            .stream()
                            .sorted(Comparator.comparing(Notification::getDateCreated)
                                    .reversed()).collect(Collectors.toList())
            );
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Notification> cq = cb.createQuery(Notification.class);
        Root<Notification> notification = cq.from(Notification.class);
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (allRequestParams.get("user") != null) {
            predicates.add(cb.equal(notification.get("userCreatedId"), Long.valueOf(allRequestParams.get("user"))));
        }
        if (allRequestParams.get("topic") != null) {
            predicates.add(cb.equal(notification.get("topic"), allRequestParams.get("topic")));
        }
        if (allRequestParams.get("date") != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            predicates.add(cb.equal(notification.get("dateCreated"),
                    LocalDateTime.parse(allRequestParams.get("date"), formatter)));
        }
        cq.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Notification> query = em.createQuery(cq);
        List<Notification> notif = query.getResultList();
        return new PageImpl<Notification>(notif);
    }


}
