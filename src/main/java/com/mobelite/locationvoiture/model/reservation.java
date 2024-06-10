package com.mobelite.locationvoiture.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data

@Entity
@Table
public class reservation {
    @Id
    @GeneratedValue
    private Long id;
    private Long carId;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated (EnumType.STRING)
    private reservationStatus reservationStatus;
    @ManyToOne
    @JoinColumn(name = "clientId")
    private client client;

}
