package com.mobelite.locationvoiture.service.impl;

import com.mobelite.locationvoiture.dto.ReservationDto;
import com.mobelite.locationvoiture.exception.EntityNotFoundException;
import com.mobelite.locationvoiture.exception.EntityNotValidException;
import com.mobelite.locationvoiture.exception.ErrorCodes;
import com.mobelite.locationvoiture.model.Car;
import com.mobelite.locationvoiture.model.Client;
import com.mobelite.locationvoiture.model.Reservation;
import com.mobelite.locationvoiture.repository.reservationRepository;
import com.mobelite.locationvoiture.service.reservationService;
import com.mobelite.locationvoiture.validators.ReservationValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class reservationServiceImpl implements reservationService {
    private final reservationRepository reservationRepository;
    @Autowired
    public reservationServiceImpl(reservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public ReservationDto save(ReservationDto reservation) {
        List<String> errors = ReservationValidator.validation(reservation);
        if (!errors.isEmpty()) {
            log.error("Reservation Validation errors: {}", errors);
            throw new EntityNotValidException("L'entite reservation n'est pas valide", ErrorCodes.RESERVATION_NOT_VALID,errors);
        }
        return ReservationDto.fromEntity(reservationRepository.save(ReservationDto.toEntity(reservation)));
    }

    @Override
    public ReservationDto getReservation(Long id) {
        if (id == null){
            log.error("reservation id is null");
            return null;
        }
        Optional<Reservation> reservation = reservationRepository.findById(id);
        return Optional.of(ReservationDto.fromEntity(reservation.get())).orElseThrow(()->
                new EntityNotFoundException("La reservation avec l'ID "+id+"n'esxicte pas dans le BD",ErrorCodes.RESERVATION_NOT_FOUND));
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReservation(Long id) {
        if (id==null || !reservationRepository.existsById(id)) {log.error("id reservation not valid");return;}
        reservationRepository.deleteById(id);
    }

    @Override
    public void updateStartDate(Long reservationId, LocalDateTime newStartDate) {
        if (reservationId==null || !reservationRepository.existsById(reservationId)) {log.error("id reservation not valid");return;}
        reservationRepository.updateStartDate(reservationId,newStartDate);
    }

    @Override
    public void updateEndDate(Long reservationId, LocalDateTime newEndDate) {
        if (reservationId==null || !reservationRepository.existsById(reservationId)) {}
        reservationRepository.updateEndDate(reservationId,newEndDate);
    }

    @Override
    public Client getReservedClient(Long id) {
        if (id==null || !reservationRepository.existsById(id)) {log.error("id reservation not valid");return null;}
        return reservationRepository.findById(id).get().getClient();
    }

    @Override
    public Car getReservedCar(Long id) {
        if (id==null || !reservationRepository.existsById(id)) {log.error("id reservation not valid");return null;}
        return reservationRepository.findById(id).get().getCar();
    }
}
