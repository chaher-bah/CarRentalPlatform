package com.mobelite.locationvoiture.service;

import com.mobelite.locationvoiture.dto.ReservationDto;
import com.mobelite.locationvoiture.model.Car;
import com.mobelite.locationvoiture.model.Client;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {
    public ReservationDto save(ReservationDto reservation);
    public ReservationDto getReservation(Long id);
    public List<ReservationDto> getAllReservations();
    public void deleteReservation(Long id);
    public void updateStartDate(Long reservationId, LocalDateTime newStartDate);
    public void updateEndDate(Long reservationId, LocalDateTime newEndDate);
    public Client getReservedClient(Long id);
    public Car getReservedCar(Long id);

}
