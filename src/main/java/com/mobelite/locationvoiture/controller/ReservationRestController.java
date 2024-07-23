package com.mobelite.locationvoiture.controller;
import com.mobelite.locationvoiture.dto.CarDto;
import com.mobelite.locationvoiture.dto.ClientDto;
import com.mobelite.locationvoiture.dto.ReservationDto;
import com.mobelite.locationvoiture.exception.EntityNotFoundException;
import com.mobelite.locationvoiture.model.Car;
import com.mobelite.locationvoiture.model.Client;
import com.mobelite.locationvoiture.model.Reservation;
import com.mobelite.locationvoiture.repository.clientRepository;
import com.mobelite.locationvoiture.model.reservationStatus;
import com.mobelite.locationvoiture.service.ReservationService;
import com.mobelite.locationvoiture.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mobelite.locationvoiture.utils.constants.APP_ROUTE;
//to get access in the front
@CrossOrigin(origins = "*")
@RestController
public class ReservationRestController {
    private final ReservationService reservationService;
    private final ClientService clientService;

    @Autowired
    public ReservationRestController(ReservationService reservationService, ClientService clientService) {
        this.reservationService = reservationService;
        this.clientService = clientService;
    }
    @PostMapping(APP_ROUTE+"/reservation/ajouter")
    public ResponseEntity<ReservationDto> save(@RequestBody ReservationDto reservation){
        ReservationDto savedReservation = reservationService.save(reservation);
        return new ResponseEntity<>(savedReservation, HttpStatus.CREATED);    }
    /*
     *@get :to get a reservation
     * @parm :the id of the reservation
     */
    @GetMapping(APP_ROUTE+"/reservation/{reservationid}")
//    @PreAuthorize("hasRole('Admin_Role')")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable("reservationid") Long id){
        return new ResponseEntity<>(reservationService.getReservation(id), HttpStatus.OK);
    }
    /*
     *@get all the reservation exists in the database
     */
    @GetMapping(APP_ROUTE+"/reservation")
//    @PreAuthorize("hasRole('Admin_Role')")
    public ResponseEntity<List<ReservationDto>> getAllReservations(){
        return new ResponseEntity<>(reservationService.getAllReservations(), HttpStatus.OK);
    }
    /*
     *@delete a reservation by it's id
     */
    @DeleteMapping(APP_ROUTE+"/reservation/{reservationid}")
    public void deleteReservation(@PathVariable("reservationid") Long id){
        reservationService.deleteReservation(id);
    }
    /*
     *@update the start date
     * @parm: the reservation id and the new start date
     */
    @PatchMapping(APP_ROUTE+"/reservation/{reservationid}/startdate")
    public ResponseEntity<String> updateStartDate(@PathVariable("reservationid") Long reservationId, @RequestBody UpdateDateRequest newStartDateRequest){
        ReservationDto reservation = reservationService.getReservation(reservationId);
        LocalDateTime newStartDate = newStartDateRequest.getDate();

        if (newStartDate.isAfter(reservation.getEndDate())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New start date cannot be after the end date");
        }

        reservationService.updateStartDate(reservationId, newStartDate);
        return ResponseEntity.ok("Start date updated successfully");
    }
    /*
     *@update the end date
     * @parm: the reservation id and the new end date
     */
    @PatchMapping(APP_ROUTE+"/reservation/{reservationid}/enddate")
    public ResponseEntity<String> updateEndDate(@PathVariable("reservationid") Long reservationId, @RequestBody UpdateDateRequest newEndDateRequest){
        ReservationDto reservation = reservationService.getReservation(reservationId);
        LocalDateTime newEndDate = newEndDateRequest.getDate();

        if (newEndDate.isBefore(reservation.getStartDate())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New end date cannot be before the start date");
        }

        reservationService.updateEndDate(reservationId, newEndDate);
        return ResponseEntity.ok("End date updated successfully");    }
    /*
    @get :the client of a reservation
    @parm:the id of a reservation
     */
    @GetMapping(APP_ROUTE+"/reservation/client/{clientid}")
    public ResponseEntity<List<ReservationDto>> getReservedClient(@PathVariable("clientid") Long id){
        List<ReservationDto> reservations = reservationService.getReservedClient(id);
        return new ResponseEntity<>(reservations, HttpStatus.OK);    }
    /*
    @get :the reserved car for this reservation
    @parm:the id of a reservation
     */
    @GetMapping(APP_ROUTE+"/reservation/{reservationid}/car")
    public ResponseEntity<Car> getReservedCar(@PathVariable("reservationid") Long id){
        return new ResponseEntity<>(reservationService.getReservedCar(id),HttpStatus.OK);
    }
    /*
    @update: the status of reservation
    @param:the reservation id and the status wanted to be
     */
    @PatchMapping(APP_ROUTE+"/reservation/status/{reservationid}")
    ResponseEntity<String> updateReservationStatus(@PathVariable("reservationid") Long reservationid,@RequestBody UpdateStatusRequest newReservationStatusRequest){

        String statusStr = newReservationStatusRequest.getStatus();
        reservationStatus newReservationStatus;

        try {
            newReservationStatus = reservationStatus.valueOf(statusStr);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid status value");
        }
        ReservationDto reservation = reservationService.getReservation(reservationid);
        ClientDto client=reservation.getClient();
        if (client.getId() == null) {
            client = clientService.save(client);
            reservation.setClient(client);
        }
        reservation.setReservationStatus(newReservationStatus);
        List<ReservationDto> reservations = client.getReservations();
        if (reservations == null) {
            reservations = new ArrayList<>();
        }
        reservations.add(reservation);
        reservation.getClient().setReservations(reservations);
        reservationService.save(reservation);
        return ResponseEntity.ok("Status successfully");
    }

    @PutMapping(APP_ROUTE+"/reservation/update/{reservationid}")
    ResponseEntity<ReservationDto> UpdateReservationData(@PathVariable ("reservationid")Long reservationid, @RequestBody ReservationDto reservationDto){
        ReservationDto reservation = reservationService.updateReservation(reservationid,reservationDto);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }


//    @PreAuthorize("hasRole('Admin_Role')")
    public static  class UpdateStatusRequest{
        private String status;
        public String getStatus(){return status;}
    }

    public static class UpdateDateRequest {
        private LocalDateTime date;

        public LocalDateTime getDate() {
            return date;
        }

        public void setDate(LocalDateTime date) {
            this.date = date;
        }
    }

}