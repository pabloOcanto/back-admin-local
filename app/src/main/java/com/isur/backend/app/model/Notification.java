package com.isur.backend.app.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class Notification implements Cloneable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "topic")
    private String topic;

    @Column(name = "title")
    private String title;

    @Column(name = "area")
    private String area;

    @Column(name = "description")
    private String description;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "user_created_id")
    private Long userCreatedId;

    @Column(name = "status")
    private String status;

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}