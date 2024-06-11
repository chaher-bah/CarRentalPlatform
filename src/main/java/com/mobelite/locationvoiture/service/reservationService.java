package com.mobelite.locationvoiture.service;

import com.mobelite.locationvoiture.model.Car;
import com.mobelite.locationvoiture.model.Client;
import com.mobelite.locationvoiture.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface reservationService {
    public void save(Reservation reservation);
    public Optional<Reservation> getReservation(Long id);
    public List<Reservation> getAllReservations();
    public void deleteReservation(Long id);
    public void updateStartDate(Long reservationId, LocalDateTime newStartDate);
    public void updateEndDate(Long reservationId, LocalDateTime newEndDate);
    public Client getReservedClient(Long id);
    public Car getReservedCar(Long id);

}
