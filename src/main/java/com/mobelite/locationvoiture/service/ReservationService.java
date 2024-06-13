package com.mobelite.locationvoiture.service;

import com.mobelite.locationvoiture.dto.ReservationDto;
import com.mobelite.locationvoiture.model.Car;
import com.mobelite.locationvoiture.model.Client;
import com.mobelite.locationvoiture.model.reservationStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {
    ReservationDto save(ReservationDto reservation);
    ReservationDto getReservation(Long resrevationid);
    List<ReservationDto> getAllReservations();
    void deleteReservation(Long id);
    void updateStartDate(Long reservationId, LocalDateTime newStartDate);
    void updateEndDate(Long reservationId, LocalDateTime newEndDate);
    Client getReservedClient(Long reservationid);
    Car getReservedCar(Long resrevationid);
    ReservationDto updateReservationStatus(Long reservationid, reservationStatus reservationStatus);

}
