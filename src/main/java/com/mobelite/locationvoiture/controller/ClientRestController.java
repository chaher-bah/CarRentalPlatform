package com.mobelite.locationvoiture.controller;

import com.mobelite.locationvoiture.dto.ClientDto;
import com.mobelite.locationvoiture.dto.ReservationDto;
import com.mobelite.locationvoiture.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ClientDto> save(@RequestBody ClientDto client){
        return new ResponseEntity<>(clientService.save(client), HttpStatus.CREATED);
    }
    @GetMapping(APP_ROUTE+"/client/{clientid}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable("clientid") Long id){
        return new ResponseEntity<>(clientService.getClientById(id), HttpStatus.OK);
    }
    @GetMapping(APP_ROUTE+"/client/{clientid}/permis")
    public ResponseEntity<byte[]> getPermisImage(@PathVariable("clientid") Long id){
        return new ResponseEntity<>(clientService.getPermisImage(id), HttpStatus.OK);
    }
    @GetMapping(APP_ROUTE+"/client/{clientid}/reservation")
    public ResponseEntity<List<ReservationDto>> getAllReservations(@PathVariable("clientid") Long clientId){
        return new ResponseEntity<>(clientService.getAllReservations(clientId), HttpStatus.OK);
    }
    @GetMapping(APP_ROUTE+"/client/{email}")
    public ResponseEntity<ClientDto> getClientByEmail(@PathVariable("email") String email){
        return new ResponseEntity<>(clientService.getClientByEmail(email), HttpStatus.OK);
    }
    @GetMapping(APP_ROUTE+"/client/{cin}")
    public ResponseEntity<ClientDto> getClientByCin(@PathVariable("cin") String cin){
        return new ResponseEntity<>(clientService.getClientByCin(cin), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('Admin_Role')")
    @GetMapping(APP_ROUTE+"/client/all")
    public ResponseEntity<List<ClientDto>> getAllClients(){
        return new ResponseEntity<>(clientService.getAllClients(), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('Admin_Role')")
    @DeleteMapping(APP_ROUTE+"/client/delete/{clientid}")
    public void deleteById(@PathVariable("clientid") Long id){
        clientService.deleteById(id);
    }


}
