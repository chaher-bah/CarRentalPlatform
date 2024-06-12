package com.mobelite.locationvoiture.service;

import com.mobelite.locationvoiture.dto.ReservationDto;
import com.mobelite.locationvoiture.model.Car;
import com.mobelite.locationvoiture.model.Client;
import com.mobelite.locationvoiture.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface reservationService {
    public ReservationDto save(ReservationDto reservation);
    public ReservationDto getReservation(Long id);
    public List<ReservationDto> getAllReservations();
    public void deleteReservation(Long id);
    public void updateStartDate(Long reservationId, LocalDateTime newStartDate);
    public void updateEndDate(Long reservationId, LocalDateTime newEndDate);
    public Client getReservedClient(Long id);
    public Car getReservedCar(Long id);

}
