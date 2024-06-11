package com.mobelite.locationvoiture.service.impl;

import com.mobelite.locationvoiture.model.Car;
import com.mobelite.locationvoiture.model.Client;
import com.mobelite.locationvoiture.model.Reservation;
import com.mobelite.locationvoiture.repository.reservationRepository;
import com.mobelite.locationvoiture.service.reservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class reservationServiceImpl implements reservationService {
    private final reservationRepository reservationRepository;
    @Autowired
    public reservationServiceImpl(reservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void save(Reservation reservation) {
        //todo exception handling
        reservationRepository.save(reservation);
    }

    @Override
    public Optional<Reservation> getReservation(Long id) {
        //to do exception
        return reservationRepository.findById(id);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public void updateStartDate(Long reservationId, LocalDateTime newStartDate) {
        reservationRepository.updateStartDate(reservationId,newStartDate);
    }

    @Override
    public void updateEndDate(Long reservationId, LocalDateTime newEndDate) {
        reservationRepository.updateEndDate(reservationId,newEndDate);
    }

    @Override
    public Client getReservedClient(Long id) {
        return reservationRepository.findById(id).get().getClient();
    }

    @Override
    public Car getReservedCar(Long id) {
        return reservationRepository.findById(id).get().getCar();
    }
}
