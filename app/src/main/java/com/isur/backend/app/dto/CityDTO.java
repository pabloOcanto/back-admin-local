package com.isur.backend.app.dto;

import lombok.Data;

@Data
public class CityDTO {

    private Float lat;
    private Float lon;
    private String city;
    private String state;
    private String status;
}
