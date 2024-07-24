package com.mobelite.locationvoiture.service.impl;

import com.mobelite.locationvoiture.dto.CarDto;
import com.mobelite.locationvoiture.service.ClientService;
import com.mobelite.locationvoiture.dto.ClientDto;
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
import com.mobelite.locationvoiture.repository.carRepository;
import com.mobelite.locationvoiture.repository.clientRepository;
import com.mobelite.locationvoiture.service.ReservationService;
import com.mobelite.locationvoiture.validators.ReservationValidator;
import jakarta.security.auth.message.module.ClientAuthModule;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReservationServiceImpl implements ReservationService {
    private final reservationRepository reservationRepository;
    public final clientRepository clientRepository;
    public final carRepository carRepository;
    private final ClientService clientService;

    @Autowired
    public ReservationServiceImpl(reservationRepository reservationRepository, clientRepository clientRepository, carRepository carRepository, ClientService clientService) {
        this.reservationRepository = reservationRepository;
        this.clientRepository = clientRepository;
        this.carRepository=carRepository;
        this.clientService = clientService;
    }
    @Override
    public ReservationDto save(ReservationDto reservationDto) {
        List<String> errors = ReservationValidator.validation(reservationDto);
        if (!errors.isEmpty()) {
            log.error("Reservation Validation errors: {}", errors);
            throw new EntityNotValidException("L'entité reservation n'est pas valide", ErrorCodes.RESERVATION_NOT_VALID, errors);
        }
        Reservation reservation = ReservationDto.toEntity(reservationDto);
        reservation.setStartDate(reservationDto.getStartDate());
        reservation.setEndDate(reservationDto.getEndDate());
        reservation.setReservationStatus(reservationDto.getReservationStatus());
        // Fetch the existing client and car from the database
        Long id = reservationDto.getClient().getId();
        if (id ==null){
            Client newClient = ClientDto.toEntity(reservationDto.getClient());
            newClient = clientRepository.save(newClient);
            newClient.addReservation(reservation);
            reservation.setClient(newClient);
        }
        else{
        Client client = clientRepository.findById(reservationDto.getClient().getId())
                .orElseGet(() -> {
                    Client newClient = ClientDto.toEntity(reservationDto.getClient());
                    newClient = clientRepository.save(newClient);
                    return newClient;
                });
        client.addReservation(reservation);
        reservation.setClient(client);}

        Car car = carRepository.findById(reservationDto.getCar().getId())
                .orElseThrow(() -> new EntityNotFoundException("Car not found", ErrorCodes.CAR_NOT_FOUND));
        reservation.setCar(car);

        reservation.getClient().addReservation(reservation);
        return ReservationDto.fromEntity(reservationRepository.save(reservation));
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
    public List<ReservationDto> getReservedClient(Long clientid) {
        if (clientid==null || !clientRepository.existsById(clientid) ){log.error("id client not valid cannot get his reservations");
            throw new InvalidOperationException("id client not valid cannot get reservation",ErrorCodes.CLIENT_NOT_VALID);}
        Client client = clientRepository.findById(clientid).orElseThrow(() ->
                new EntityNotFoundException("Client with ID " + clientid + " not found", ErrorCodes.CLIENT_NOT_FOUND));

        return client.getReservations().stream()
                .map(ReservationDto::fromEntity)
                .collect(Collectors.toList());    }

    @Override
    public Car getReservedCar(Long resid) {
        if (resid==null || !reservationRepository.existsById(resid)) {log.error("id reservation not valid cannot get cars");
            throw new InvalidOperationException("id reservation not valid cannot get cars",ErrorCodes.RESERVATION_NOT_FOUND);}
        return reservationRepository.findCarById(resid);
    }


    @Override
    public void updateReservationStatus(Long reservationid , reservationStatus reservationstatus) {
        if (reservationid==null || !reservationRepository.existsById(reservationid)) {
            log.error("id reservation not valid cannot update reservation status");
            throw new InvalidOperationException("id reservation not valid cannot update status",ErrorCodes.RESERVATION_CAN_NOT_BE_MODIFIED);
        };
        if (reservationstatus == null) {
            log.error("status not valid cannot update reservation status");
            throw new InvalidOperationException("reservation status not valid cannot update status",ErrorCodes.RESERVATION_CAN_NOT_BE_MODIFIED);
        };

        ReservationDto reservation =getReservation(reservationid);
        ClientDto clientdto=reservation.getClient();
        if (clientdto.getId() == null) {
            ClientDto client = clientService.save(clientdto);
            reservation.setClient(client);
        }
        reservation.setReservationStatus(reservationstatus);
        ReservationDto.fromEntity(reservationRepository.save(ReservationDto.toEntity(reservation)));
    }

    @Override
    public ReservationDto updateReservation(Long id, ReservationDto reservationDto) {
        List<String> errors = ReservationValidator.validation(reservationDto);
        if (!errors.isEmpty()) {
            throw new EntityNotValidException("L'entité reservation n'est pas valide", ErrorCodes.RESERVATION_NOT_VALID, errors);
        }

        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found", ErrorCodes.RESERVATION_NOT_FOUND));

        Reservation reservation = ReservationDto.toEntity(reservationDto);
        reservation.setId(existingReservation.getId());
        reservation.setStartDate(reservationDto.getStartDate());
        reservation.setEndDate(reservationDto.getEndDate());
        reservation.setReservationStatus(reservationDto.getReservationStatus());

        Client client = clientRepository.findById(reservationDto.getClient().getId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found", ErrorCodes.CLIENT_NOT_FOUND));
        reservation.setClient(client);

        Car car = carRepository.findById(reservationDto.getCar().getId())
                .orElseThrow(() -> new EntityNotFoundException("Car not found", ErrorCodes.CAR_NOT_FOUND));
        reservation.setCar(car);

        Reservation updatedReservation = reservationRepository.save(reservation);
        return ReservationDto.fromEntity(updatedReservation);
    }

}