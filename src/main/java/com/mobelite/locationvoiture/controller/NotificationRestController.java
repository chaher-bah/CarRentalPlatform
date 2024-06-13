package com.mobelite.locationvoiture.controller;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public NotificationDto save(@RequestBody NotificationDto notification){
        return notificationService.save(notification);
    }
    @DeleteMapping(APP_ROUTE+"/notification/delete/{notifid}")
    public void delete(@PathVariable("notifid") Long notificationId){
        notificationService.delete(notificationId);
    }
    @PreAuthorize("hasRole('Admin_Role')")
    @GetMapping(APP_ROUTE+"/notification/all")
    public List<NotificationDto> getAll(){
        return notificationService.getAll();
    }
    @PreAuthorize("hasRole('Admin_Role')")
    @GetMapping(APP_ROUTE+"/notification/{notifid}")
    public NotificationDto getById(@PathVariable("notifid") Long id){
        return notificationService.getById(id);
    }
    @GetMapping(APP_ROUTE+"/notification/clients/{clientid}")
    public List<NotificationDto> getByClient(@PathVariable("clientid") Long clientId){
        return notificationService.getByClient(clientId);
    }

    @PreAuthorize("hasRole('Admin_Role')")
    @GetMapping(APP_ROUTE+"/notification/admins/{adminid}")
    public List<NotificationDto> getByAdmin(@PathVariable("adminid") Long adminId){
        return notificationService.getByAdmin(adminId);
    }
}
