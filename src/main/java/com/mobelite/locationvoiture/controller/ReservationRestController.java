package com.mobelite.locationvoiture.controller;
import com.mobelite.locationvoiture.dto.ReservationDto;
import com.mobelite.locationvoiture.model.Car;
import com.mobelite.locationvoiture.model.Client;
import com.mobelite.locationvoiture.model.reservationStatus;
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
    /*
    *@get :to get a reservation
    * @parm :the id of the reservation
     */
    @GetMapping(APP_ROUTE+"/reservation/{reservationid}")
    @PreAuthorize("hasRole('Admin_Role')")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable("reservationid") Long id){
        return new ResponseEntity<>(reservationService.getReservation(id), HttpStatus.OK);
    }
    /*
    *@get all the reservation exists in the database
     */
    @GetMapping(APP_ROUTE+"/reservation/all")
    @PreAuthorize("hasRole('Admin_Role')")
    public ResponseEntity<List<ReservationDto>> getAllReservations(){
        return new ResponseEntity<>(reservationService.getAllReservations(), HttpStatus.OK);
    }
    /*
    *@delete a reservation by it's id
     */
    @DeleteMapping(APP_ROUTE+"/reservation/delete/{reservationid}")
    public void deleteReservation(@PathVariable("reservationid") Long id){
        reservationService.deleteReservation(id);
    }
    /*
    *@update the start date
    * @parm: the reservation id and the new start date
     */
    @PatchMapping(APP_ROUTE+"/reservation/{reservationid}/update/startdate")
    public void updateStartDate(@PathVariable("reservationid") Long reservationId, LocalDateTime newStartDate){
        reservationService.updateStartDate(reservationId, newStartDate);
    }
    /*
     *@update the end date
     * @parm: the reservation id and the new end date
     */
    @PatchMapping(APP_ROUTE+"/reservation/{reservationid}/update/enddate")
    public void updateEndDate(@PathVariable("reservationid") Long reservationId, LocalDateTime newEndDate){
        reservationService.updateEndDate(reservationId, newEndDate);
    }
    /*
    @get :the client of a reservation
    @parm:the id of a reservation
     */
    @GetMapping(APP_ROUTE+"/reservation/{reservationid}")
    public ResponseEntity<Client> getReservedClient(@PathVariable("reservationid") Long id){
        return new ResponseEntity<>(reservationService.getReservedClient(id),HttpStatus.OK);
    }
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
    @PreAuthorize("hasRole('Admin_Role')")
    @PatchMapping(APP_ROUTE+"reservation/{reservationid}/update/{status}")
    ReservationDto updateReservationStatus(@PathVariable("reservationid") Long reservationid,
                                           @PathVariable("status") reservationStatus reservationStatus){
        return reservationService.updateReservationStatus(reservationid, reservationStatus);
    }

}
