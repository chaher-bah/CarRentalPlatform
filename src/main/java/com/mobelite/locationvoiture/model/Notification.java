package com.mobelite.locationvoiture.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    private Long id;
    private String message;
    @ManyToOne(targetEntity = Client.class)
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    private Client client;
    @ManyToOne(targetEntity = Admin.class)
    @JoinColumn(name = "Admin_id")
    private Admin admin;
}
