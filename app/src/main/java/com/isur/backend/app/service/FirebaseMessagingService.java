package com.isur.backend.app.service;

import com.isur.backend.app.dto.MessageDTO;
import com.isur.backend.app.dto.NotificationFCMDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

@Service
public class FirebaseMessagingService {

    private static final Logger log = LoggerFactory.getLogger(FirebaseMessagingService.class);
    private static final String SEND_NOTIFICATION_TO_MOBILE = "https://fcm.googleapis.com/fcm/send";
    @Value("${token-header-android}")
    String TOKEN_ANDROID;
    @Value("${token-header-ios}")
    String TOKEN_IOS;
    private RestTemplate restTemplate;

    @Autowired
    public FirebaseMessagingService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void sendNotification(NotificationFCMDTO notification) throws IOException {
        sendNotification(notification, "IOS");
        sendNotification(notification, "ANDROID");
    }

    @Async
    public void sendNotification(NotificationFCMDTO notificationFCMDTO, String plataform) {
        try {
            HttpEntity<NotificationFCMDTO> entity = getNotificationHttpEntity(notificationFCMDTO, plataform);
            ResponseEntity<MessageDTO> result = restTemplate.postForEntity(SEND_NOTIFICATION_TO_MOBILE, entity, MessageDTO.class);

            if (!result.getStatusCode().is2xxSuccessful() || result.getBody().getMessage_id().equals("")) {
                // notification.setStatus("Error - Not sended");
                log.info("No se pudo enviar la notifiacion ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HttpEntity<NotificationFCMDTO> getNotificationHttpEntity(NotificationFCMDTO notificationFCMDTO, String plataform) {

        return new HttpEntity<>(notificationFCMDTO, getHttpHeaders(plataform));
    }

    private HttpHeaders getHttpHeaders(String plataform) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        if (plataform.equals("IOS")) headers.add(HttpHeaders.AUTHORIZATION, TOKEN_IOS);
        else headers.add(HttpHeaders.AUTHORIZATION, TOKEN_ANDROID);
        return headers;
    }

}
