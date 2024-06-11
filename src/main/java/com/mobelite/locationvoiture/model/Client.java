package com.mobelite.locationvoiture.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client extends User {
    private String cin;
    private String numTel;
    @OneToMany(targetEntity = Reservation.class)
    private List<Reservation> reservations;
    @Lob
    private byte[] photoPermis;
    @OneToMany(targetEntity = Notification.class)
    private List<Notification> notifications;

}
