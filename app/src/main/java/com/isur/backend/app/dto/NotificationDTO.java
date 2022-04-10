package com.isur.backend.app.dto;

import lombok.Data;

import java.util.List;

@Data
public class NotificationDTO {
    private String topic;
    private String title;
    private String message;
    private String icon;
    private String locationdescription;
    private List<CityDTO> area;
    private Long userCreatedId;
}
