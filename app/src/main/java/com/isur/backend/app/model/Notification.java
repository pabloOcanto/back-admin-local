package com.isur.backend.app.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Transient
    private String icon;

    @Transient
    private String locationdescription;

    @Column(name = "area")
    private String area;

    @Column(name = "message")
    private String message;

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