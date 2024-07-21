package com.mobelite.locationvoiture.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDate;

    @Enumerated (EnumType.STRING)
    private reservationStatus reservationStatus= com.mobelite.locationvoiture.model.reservationStatus.EN_COUR;

    //must be always eager
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Car_id")
    @ToString.Exclude
    @JsonBackReference
    private Car car;


    public BigDecimal getFraisAPayer() {
        if (startDate != null && endDate != null && car != null && car.getFraisLocation() != null) {
            long daysBetween = Duration.between(startDate, endDate).toDays();
            return car.getFraisLocation().multiply(BigDecimal.valueOf(daysBetween));
        }
        return BigDecimal.ZERO;
    }
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
