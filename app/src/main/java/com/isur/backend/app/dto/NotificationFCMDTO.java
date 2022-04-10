package com.isur.backend.app.dto;

import com.isur.backend.app.model.Notification;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import lombok.Data;

@Data
public class NotificationFCMDTO {
    private String to;
    private HashMap notification;
    private Notification data;
    @JsonProperty(value = "content_avaible")
    private boolean contentAvaible= true;
}
