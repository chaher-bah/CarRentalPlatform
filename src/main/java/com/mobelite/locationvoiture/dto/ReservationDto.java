package com.mobelite.locationvoiture.dto;

import com.mobelite.locationvoiture.model.Reservation;
import com.mobelite.locationvoiture.model.reservationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Builder
@Data
public class ReservationDto {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Enumerated(EnumType.STRING)
    private reservationStatus reservationStatus;
    private ClientDto client;
    private CarDto car;
    private BigDecimal fraisAPayer=getFraisAPayer();
    public BigDecimal getFraisAPayer() {
        if (startDate != null && endDate != null && car != null && car.getFraisLocation() != null) {
            long daysBetween = Duration.between(startDate, endDate).toDays();
            return car.getFraisLocation().multiply(BigDecimal.valueOf(daysBetween));
        }
        return BigDecimal.ZERO;
    }
    public static ReservationDto fromEntity(Reservation reservation) {
        if (reservation == null){
            return null;
        }
        return ReservationDto.builder()
                .id(reservation.getId())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .reservationStatus(reservation.getReservationStatus())
                .build();
    }
    //dto---> entity
    public static Reservation toEntity(ReservationDto reservationDto) {
        if (reservationDto == null){
            return null;
        }
        return Reservation.builder()
                .id(reservationDto.getId())
                .startDate(reservationDto.getStartDate())
                .endDate(reservationDto.getEndDate())
                .reservationStatus(reservationDto.getReservationStatus())
                .build();
    }
    //check si la reservation est confirmee
    public boolean isAccepted(){
        return reservationStatus.ACCEPTEE.equals(this.reservationStatus);
    }

}
