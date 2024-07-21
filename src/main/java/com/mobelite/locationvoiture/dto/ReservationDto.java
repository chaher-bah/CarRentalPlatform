package com.mobelite.locationvoiture.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mobelite.locationvoiture.model.Reservation;
import com.mobelite.locationvoiture.model.reservationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private reservationStatus reservationStatus;

    private ClientDto client;
    private CarDto car;
    private BigDecimal fraisAPayer;

    public static ReservationDto fromEntity(Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        return ReservationDto.builder()
                .id(reservation.getId())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .reservationStatus(reservation.getReservationStatus())
                .client(ClientDto.fromEntity(reservation.getClient()))
                .car(CarDto.fromEntity(reservation.getCar()))
                .fraisAPayer(reservation.getFraisAPayer())
                .build();
    }

    public static Reservation toEntity(ReservationDto reservationDto) {
        if (reservationDto == null) {
            return null;
        }

        Reservation Reservation = new Reservation();
        Reservation.setId(reservationDto.getId());
        Reservation.setStartDate(reservationDto.getStartDate());
        Reservation.setEndDate(reservationDto.getEndDate());
        Reservation.setReservationStatus(reservationDto.getReservationStatus());
        Reservation.setClient(ClientDto.toEntity(reservationDto.getClient()));
        Reservation.setCar(CarDto.toEntity(reservationDto.getCar()));
        return Reservation;
    }

    public boolean isAccepted() {
        return reservationStatus.ACCEPTEE.equals(this.reservationStatus);
    }
}
