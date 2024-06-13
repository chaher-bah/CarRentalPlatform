package com.mobelite.locationvoiture.controller;
import com.mobelite.locationvoiture.dto.ReservationDto;
import com.mobelite.locationvoiture.model.Car;
import com.mobelite.locationvoiture.model.Client;
import com.mobelite.locationvoiture.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.mobelite.locationvoiture.utils.constants.APP_ROUTE;

@CrossOrigin(origins = "*")
@RestController
public class ReservationRestController {
    private final ReservationService reservationService;
    @Autowired
    public ReservationRestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    @PostMapping(APP_ROUTE+"/reservation/ajouter")
    public ResponseEntity<ReservationDto> save(@RequestBody ReservationDto reservation){
        return new ResponseEntity<>(reservationService.save(reservation), HttpStatus.CREATED);
    }

    @GetMapping(APP_ROUTE+"/reservation/{reservationid}")
    @PreAuthorize("hasRole('Admin_Role')")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable("reservationid") Long id){
        return new ResponseEntity<>(reservationService.getReservation(id), HttpStatus.OK);
    }
    @GetMapping(APP_ROUTE+"/reservation/all")
    @PreAuthorize("hasRole('Admin_Role')")
    public ResponseEntity<List<ReservationDto>> getAllReservations(){
        return new ResponseEntity<>(reservationService.getAllReservations(), HttpStatus.OK);
    }
    @DeleteMapping(APP_ROUTE+"/reservation/delete/{reservationid}")
    public void deleteReservation(@PathVariable("reservationid") Long id){
        reservationService.deleteReservation(id);
    }
    @PutMapping(APP_ROUTE+"/reservation/{reservationid}/update/startdate")
    public void updateStartDate(@PathVariable("reservationid") Long reservationId, LocalDateTime newStartDate){
        reservationService.updateStartDate(reservationId, newStartDate);
    }
    @PutMapping(APP_ROUTE+"/reservation/{reservationid}/update/enddate")
    public void updateEndDate(@PathVariable("reservationid") Long reservationId, LocalDateTime newEndDate){
        reservationService.updateEndDate(reservationId, newEndDate);
    }

    @GetMapping(APP_ROUTE+"/reservation/{clientid}")
    public ResponseEntity<Client> getReservedClient(@PathVariable("clientid") Long id){
        return new ResponseEntity<>(reservationService.getReservedClient(id),HttpStatus.OK);
    }
    @GetMapping(APP_ROUTE+"/reservation/{reservationid}/car")
    public ResponseEntity<Car> getReservedCar(@PathVariable("reservationid") Long id){
        return new ResponseEntity<>(reservationService.getReservedCar(id),HttpStatus.OK);
    }
}
