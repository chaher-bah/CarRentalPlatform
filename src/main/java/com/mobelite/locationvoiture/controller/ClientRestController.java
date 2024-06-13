package com.mobelite.locationvoiture.controller;

import com.mobelite.locationvoiture.dto.ClientDto;
import com.mobelite.locationvoiture.dto.ReservationDto;
import com.mobelite.locationvoiture.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mobelite.locationvoiture.utils.constants.APP_ROUTE;

@CrossOrigin(origins = "*")
@RestController
public class ClientRestController {
    private final ClientService clientService;
    @Autowired
    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }
    @PostMapping(APP_ROUTE+"/client/ajouter")
    public ClientDto save(@RequestBody ClientDto client){
        return clientService.save(client);
    }
    @GetMapping(APP_ROUTE+"/client/{clientid}")
    public ClientDto getClientById(@PathVariable("clientid") Long id){
        return clientService.getClientById(id);
    }
    @PreAuthorize("hasRole('Admin_Role')")
    @GetMapping(APP_ROUTE+"/client/all")
    public List<ClientDto> getAllClients(){
        return clientService.getAllClients();
    }
    @PreAuthorize("hasRole('Admin_Role')")
    @DeleteMapping(APP_ROUTE+"/client/delete/{clientid}")
    public void deleteById(@PathVariable("clientid") Long id){
        clientService.deleteById(id);
    }
    @GetMapping(APP_ROUTE+"/client/{clientid}/permis")
    public byte[] getPermisImage(@PathVariable("clientid") Long id){
        return clientService.getPermisImage(id);
    }
    @GetMapping(APP_ROUTE+"/client/{clientid}/reservation")
    public List<ReservationDto> getAllReservations(@PathVariable("clientid") Long clientId){
        return clientService.getAllReservations(clientId);
    }
    @GetMapping(APP_ROUTE+"/client/{email}")
    public ClientDto getClientByEmail(@PathVariable("email") String email){
        return clientService.getClientByEmail(email);
    }
    @GetMapping(APP_ROUTE+"/client/{cin}")
    public ClientDto getClientByCin(@PathVariable("cin") String cin){
        return clientService.getClientByCin(cin);
    }

}
