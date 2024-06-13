package com.mobelite.locationvoiture.service.impl;

import com.mobelite.locationvoiture.dto.ReservationDto;
import com.mobelite.locationvoiture.exception.EntityNotFoundException;
import com.mobelite.locationvoiture.exception.EntityNotValidException;
import com.mobelite.locationvoiture.exception.ErrorCodes;
import com.mobelite.locationvoiture.exception.InvalidOperationException;
import com.mobelite.locationvoiture.model.Car;
import com.mobelite.locationvoiture.model.Client;
import com.mobelite.locationvoiture.model.Reservation;
import com.mobelite.locationvoiture.model.reservationStatus;
import com.mobelite.locationvoiture.repository.reservationRepository;
import com.mobelite.locationvoiture.service.ReservationService;
import com.mobelite.locationvoiture.validators.ReservationValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReservationServiceImpl implements ReservationService {
    private final reservationRepository reservationRepository;
    @Autowired
    public ReservationServiceImpl(reservationRepository reservationRepository) {
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
        if (id==null || !reservationRepository.existsById(id)) {log.error("id reservation not valid cannot delete");
            throw new InvalidOperationException("problem with id reservation cannot delete",ErrorCodes.RESERVATION_CAN_NOT_BE_DELETED);}
        reservationRepository.deleteById(id);
    }

    @Override
    public void updateStartDate(Long reservationId, LocalDateTime newStartDate) {
        if (reservationId==null || !reservationRepository.existsById(reservationId)) {log.error("id reservation not valid cannot update start date");
            throw new InvalidOperationException("id reservation not valid cannot update start date",ErrorCodes.RESERVATION_CAN_NOT_BE_MODIFIED);}
        reservationRepository.updateStartDate(reservationId,newStartDate);
    }

    @Override
    public void updateEndDate(Long reservationId, LocalDateTime newEndDate) {
        if (reservationId==null || !reservationRepository.existsById(reservationId)) {log.error("id reservation not valid cannot update end date");
            throw new InvalidOperationException("id reservation not valid cannot update end date",ErrorCodes.RESERVATION_CAN_NOT_BE_MODIFIED);}
        reservationRepository.updateEndDate(reservationId,newEndDate);
    }

    @Override
    public Client getReservedClient(Long id) {
        if (id==null || !reservationRepository.existsById(id)) {log.error("id reservation not valid cannot get clients");
            throw new InvalidOperationException("id reservation not valid cannot get clients",ErrorCodes.RESERVATION_NOT_FOUND);}
        return reservationRepository.findById(id).get().getClient();
    }

    @Override
    public Car getReservedCar(Long id) {
        if (id==null || !reservationRepository.existsById(id)) {log.error("id reservation not valid cannot get cars");
            throw new InvalidOperationException("id reservation not valid cannot get cars",ErrorCodes.RESERVATION_NOT_FOUND);}
        return reservationRepository.findById(id).get().getCar();
    }

    @Override
    public ReservationDto updateReservationStatus(Long reservationid , reservationStatus reservationstatus) {
        if (reservationid==null || !reservationRepository.existsById(reservationid)) {
            log.error("id reservation not valid cannot update reservation status");
            throw new InvalidOperationException("id reservation not valid cannot update status",ErrorCodes.RESERVATION_CAN_NOT_BE_MODIFIED);
        };
        if (!StringUtils.hasLength(String.valueOf(reservationstatus))) {
            log.error("status not valid cannot update reservation status");
            throw new InvalidOperationException("reservation status not valid cannot update status",ErrorCodes.RESERVATION_CAN_NOT_BE_MODIFIED);
        };
        ReservationDto reservation =getReservation(reservationid);
        reservation.setReservationStatus(reservationstatus);
        return ReservationDto.fromEntity(reservationRepository.save(ReservationDto.toEntity(reservation))
        );
    }

}
