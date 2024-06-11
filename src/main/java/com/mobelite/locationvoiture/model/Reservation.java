package com.mobelite.locationvoiture.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;
    private Long carId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Enumerated (EnumType.STRING)
    private reservationStatus reservationStatus;
    @ManyToOne(targetEntity = Client.class)
    @JoinColumn(name = "client_id")
    private Client client;
}
