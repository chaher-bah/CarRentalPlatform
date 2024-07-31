package com.mobelite.locationvoiture.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobelite.locationvoiture.dto.ClientDto;
import com.mobelite.locationvoiture.dto.ReservationDto;
import com.mobelite.locationvoiture.model.Car;
import com.mobelite.locationvoiture.model.Client;
import com.mobelite.locationvoiture.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mobelite.locationvoiture.utils.constants.APP_ROUTE;

@CrossOrigin(origins = "*")
@RestController
public class ClientRestController {
    private final ClientService clientService;
    @Autowired
    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }
    @PostMapping(APP_ROUTE+"/client/easy/ajouter")
    public ResponseEntity<ClientDto> saveeasy(@RequestBody ClientDto client){return new ResponseEntity<>(clientService.save(client), HttpStatus.CREATED);}
    @PostMapping(value = APP_ROUTE+"/client/ajouter",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Client> save(
            @RequestPart("client") String clientJson,
            @RequestPart(value = "images",required = false) List<MultipartFile>images) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Client client=mapper.readValue(clientJson, Client.class);
        Client savedClient=clientService.add(client);
        if (!(images ==null)){
        clientService.savePermisImage(savedClient.getId(),images);}

        return ResponseEntity.ok(savedClient);

    }
    /*
    @Get the client by its id
     */
    @GetMapping(APP_ROUTE+"/client/{clientid}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable("clientid") Long id){
        return new ResponseEntity<>(clientService.getClientById(id), HttpStatus.OK);
    }
    /*
    @Get the phote of the permit pass of a client
    @parm the id of a client
     */

    /*
    @Get all the reservations of a client if exicts
     */
    @GetMapping(APP_ROUTE+"/client/{clientid}/reservation")
    public ResponseEntity<List<ReservationDto>> getAllReservations(@PathVariable("clientid") Long clientId){
        return new ResponseEntity<>(clientService.getAllReservations(clientId), HttpStatus.OK);
    }
    /*
    @Get the client by his email
     */
    @GetMapping(APP_ROUTE+"/client/email/{email}")
    public ResponseEntity<List<ClientDto>> getClientByEmail(@PathVariable("email") String email){
        return new ResponseEntity<>(clientService.getClientByEmail(email), HttpStatus.OK);
    }
    @GetMapping(APP_ROUTE+"/client/check")
    public ResponseEntity<Map<String, Boolean>> checkClientExists(@RequestParam String email) {
        boolean exists = clientService.clientExistsByEmail(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }
    /*
    @get the client by his cin /passsport number if not tunien
     */
    @GetMapping(APP_ROUTE+"/client/cin/{cin}")
    public ResponseEntity<List<ClientDto>> getClientByCin(@PathVariable("cin") String cin){
        return new ResponseEntity<>(clientService.getClientByCin(cin), HttpStatus.OK);
    }
    /*
    @get all the clients that exicts in the data base
     */
//    @PreAuthorize("hasRole('Admin_Role')")
    @GetMapping(APP_ROUTE+"/client")
    public ResponseEntity<List<ClientDto>> getAllClients(){
        return new ResponseEntity<>(clientService.getAllClients(), HttpStatus.OK);
    }
    /*
    @Delete the client by his id
     */
//    @PreAuthorize("hasRole('Admin_Role')")
    @DeleteMapping(APP_ROUTE+"/client/{clientid}")
    public void deleteById(@PathVariable("clientid") Long id){
        clientService.deleteById(id);
    }



    @PostMapping(APP_ROUTE + "/client/{clientid}/uploadPermis")
    public ResponseEntity<String> uploadPermisImage(@PathVariable("clientid") Long id, @RequestParam("files") List<MultipartFile> files) {
        try {
            clientService.savePermisImage(id, files);
            return new ResponseEntity<>("Files uploaded successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value=APP_ROUTE + "/client/{clientid}/images")
    public ResponseEntity<List<byte[]>> getPermisImages(@PathVariable("clientid") Long id) {
        List<byte[]> images = clientService.getPermisImages(id);
        if (images != null ) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(images);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = APP_ROUTE + "/client/{id}/images/{imageIndex}")
    public ResponseEntity<byte[]> getClientImage(@PathVariable("id") Long id, @PathVariable("imageIndex") int imageIndex) {
        byte[] imageBytes = clientService.getPermisImage(id, imageIndex);
        if (imageBytes != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"image_" + id + "_" + imageIndex + ".jpg\"")
                    .body(imageBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(APP_ROUTE+"/client/nom_prenom")
    public ResponseEntity<List<ClientDto>> getByNomPrenom(@RequestParam(required = false) String nom, @RequestParam(required = false) String prenom){
        return new ResponseEntity<>(clientService.getClientByNomOrPrenom(nom,prenom), HttpStatus.OK);
    }

    @PutMapping(value = APP_ROUTE+"/client/update/{clientId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Client> updateClient(
            @PathVariable("clientId") Long id,
            @RequestPart("client") String clientJson,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Client client = objectMapper.readValue(clientJson, Client.class);
            Client updatedClient = clientService.update(id, client, images);
            return ResponseEntity.ok(updatedClient);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
