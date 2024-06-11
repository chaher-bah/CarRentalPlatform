package com.mobelite.locationvoiture.model;

import jakarta.persistence.*;
import lombok.Data;

@Data

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    private Long id;
    private String message;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;
}
