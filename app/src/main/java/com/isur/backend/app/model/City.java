package com.isur.backend.app.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class City {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "lat")
    private Float lat;

    @Column(name = "lon")
    private Float lon;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "status")
    private String status;
}
