package com.isur.backend.app.service;

import com.isur.backend.app.dto.MessageDTO;
import com.isur.backend.app.dto.NotificationMobileDTO;
import com.isur.backend.app.exception.NotificationMobileException;
import com.isur.backend.app.model.Notification;
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
import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseMessagingService {

    private static final Logger log = LoggerFactory.getLogger(FirebaseMessagingService.class);
    private static final String SEND_NOTIFICATION_TO_MOBILE = "https://fcm.googleapis.com/fcm/send";

    private RestTemplate restTemplate;

    @Value("${token-header}")
    String token;

    @Autowired
    public FirebaseMessagingService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity sendNotification(Notification notification) throws IOException {
        callSendNotificationToMobile1(notificationMobileBuilder(notification));
        ResponseEntity response = callSendNotificationToMobile2(notificationMobileBuilder(notification));
        return response;
    }
    @Async
    public void callSendNotificationToMobile1 (NotificationMobileDTO mobileDTO) throws IOException {
        HttpEntity<NotificationMobileDTO> entity = getNotificationHttpEntity(mobileDTO);
        ResponseEntity<MessageDTO> result = restTemplate.postForEntity(SEND_NOTIFICATION_TO_MOBILE, entity, MessageDTO.class);
    }

    public ResponseEntity callSendNotificationToMobile2 (NotificationMobileDTO mobileDTO) {

        HttpEntity<NotificationMobileDTO> entity = getNotificationHttpEntity(mobileDTO);
        ResponseEntity<MessageDTO> result = restTemplate.postForEntity(SEND_NOTIFICATION_TO_MOBILE, entity, MessageDTO.class);

        if(!result.getStatusCode().is2xxSuccessful() || result.getBody().getMessage_id().equals("")) {
         // notification.setStatus("Error - Not sended");
            throw new NotificationMobileException("No se pudo enviar la notifiacion - ID: " /* + notification.getId().toString() */ );
        }
        return ResponseEntity.ok("Se envio notificacion correctamente");
    }

    private HttpEntity<NotificationMobileDTO> getNotificationHttpEntity(NotificationMobileDTO mobileDTO) {
        return new HttpEntity<>(mobileDTO, getHttpHeaders());
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add(HttpHeaders.AUTHORIZATION, token);
        return headers;
    }

    private NotificationMobileDTO notificationMobileBuilder(Notification notification){
        NotificationMobileDTO mobileDTO = new NotificationMobileDTO();
        mobileDTO.setTo("/topics/"+notification.getTopic());

        Map<String, String> notificationMap  = new HashMap<String, String>() {{
            put("title", notification.getTitle());
            put("sound", "Tri-tone");
        }};
        mobileDTO.setNotification(notificationMap);

        Map<String, String> notificationData  = new HashMap<String, String>() {{
            put("title", notification.getTitle());
            put("type", notification.getTopic());
            put("area", notification.getArea());
            put("description", notification.getDescription());
        }};
        mobileDTO.setData(notificationData);
        return mobileDTO;
    }
}
