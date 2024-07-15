package com.mobelite.locationvoiture.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Enumerated (EnumType.STRING)
    private reservationStatus reservationStatus;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    //@ToString.Exclude
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Car_id")
    @ToString.Exclude
    private Car car;


//    public BigDecimal getFraisAPayer() {
//        if (startDate != null && endDate != null && car != null && car.getFraisLocation() != null) {
//            long daysBetween = Duration.between(startDate, endDate).toDays();
//            return car.getFraisLocation().multiply(BigDecimal.valueOf(daysBetween));
//        }
//        return BigDecimal.ZERO;
//    }
    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", reservationStatus=" + reservationStatus +
                '}';
    }
}
