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

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private reservationStatus reservationStatus;

    private ClientDto client;
    private CarDto car;
//    private BigDecimal fraisAPayer;

    public static ReservationDto fromEntity(Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        return ReservationDto.builder()
                .id(reservation.getId())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .reservationStatus(reservation.getReservationStatus())
                .client(ClientDto.clientEntityToDto(reservation.getClient()))
                .car(CarDto.fromEntity(reservation.getCar()))
//                .fraisAPayer(reservation.getFraisAPayer())
                .build();
    }

    public static Reservation toEntity(ReservationDto reservationDto) {
        if (reservationDto == null) {
            return null;
        }

        return Reservation.builder()
                .id(reservationDto.getId())
                .startDate(reservationDto.getStartDate())
                .endDate(reservationDto.getEndDate())
                .reservationStatus(reservationDto.getReservationStatus())
                .client(ClientDto.toEntity(reservationDto.getClient()))
                .car(CarDto.toEntity(reservationDto.getCar()))
                .build();
    }

    public boolean isAccepted() {
        return reservationStatus.ACCEPTEE.equals(this.reservationStatus);
    }
}
