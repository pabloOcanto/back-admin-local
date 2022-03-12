package com.isur.backend.app.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class NotificationMobileDTO implements Serializable {

    private String to;
    private Map<String, String> data;
    private Map<String, String> notification;
}