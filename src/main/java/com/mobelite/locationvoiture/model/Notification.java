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
    @ManyToOne(targetEntity = Client.class)
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne(targetEntity = Admin.class)
    @JoinColumn(name = "Admin_id")
    private Admin admin;
}
