package com.mobelite.locationvoiture.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import com.mobelite.locationvoiture.dto.NotificationDto;
import com.mobelite.locationvoiture.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mobelite.locationvoiture.utils.constants.APP_ROUTE;

@CrossOrigin (origins = "*")
@RestController
public class NotificationRestController {
    private final NotificationService notificationService;
    @Autowired
    public NotificationRestController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @PostMapping(APP_ROUTE+"/notification/ajouter")
    public ResponseEntity<NotificationDto> save(@RequestBody NotificationDto notification){
        return new ResponseEntity<>(notificationService.save(notification),HttpStatus.CREATED);
    }
    /*
    @Get all the notification send
    *an admin role
     */
//    @PreAuthorize("hasRole('Admin_Role')")
    @GetMapping(APP_ROUTE+"/notification")
    public ResponseEntity<List<NotificationDto>> getAll(){
        return new ResponseEntity<>(notificationService.getAll(),HttpStatus.OK);
    }
    /*
    @get any notification by its id
    *an admin role
     */
//    @PreAuthorize("hasRole('Admin_Role')")
    @GetMapping(APP_ROUTE+"/notification/{notifid}")
    public ResponseEntity<NotificationDto> getById(@PathVariable("notifid") Long id){
        return new ResponseEntity<>(notificationService.getById(id),HttpStatus.OK);
    }
    /*
    @get a specified client's notification
     */
    @GetMapping(APP_ROUTE+"/notification/clients/{clientid}")
    public ResponseEntity<List<NotificationDto>> getByClient(@PathVariable("clientid") Long clientId){
        return new ResponseEntity<>(notificationService.getByClient(clientId),HttpStatus.OK);
    }
    /*
    @get a specified admin's notification
     */
//    @PreAuthorize("hasRole('Admin_Role')")
    @GetMapping(APP_ROUTE+"/notification/admins/{adminid}")
    public ResponseEntity<List<NotificationDto>> getByAdmin(@PathVariable("adminid") Long adminId){
        return new ResponseEntity<>(notificationService.getByAdmin(adminId),HttpStatus.OK);
    }

    @DeleteMapping(APP_ROUTE+"/notification/{notifid}")
    public void delete(@PathVariable("notifid") Long notificationId){
        notificationService.delete(notificationId);
    }
}
