package com.isur.backend.app.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class NotificationFCMDTO implements Serializable {
    private String to;
    private Map<String, Object> notification;
    private Map<String, Object> data;
    private boolean content_avaible = true;
}
