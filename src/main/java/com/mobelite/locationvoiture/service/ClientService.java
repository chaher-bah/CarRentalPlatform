package com.mobelite.locationvoiture.service;

import com.mobelite.locationvoiture.dto.ClientDto;
import com.mobelite.locationvoiture.dto.ReservationDto;
import com.mobelite.locationvoiture.model.Client;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClientService {
    ClientDto save(ClientDto client);
    ClientDto getClientById(Long id);
    List<ClientDto> getAllClients();
    void deleteById(Long id);
    byte[] getPermisImage(Long id,int imgIndex);
    List<byte[]> getPermisImages(Long clientId);
    List<ReservationDto> getAllReservations(Long clientId);
    List<ClientDto> getClientByCin(String cin);
    List<ClientDto> getClientByEmail(String email);
    void savePermisImage(Long id, List<MultipartFile> imagefiles);
    List<ClientDto> getClientByNomOrPrenom(String nom, String prenom);
    Client add(Client client);
    Client update(Long id,Client client,List<MultipartFile> imagefiles);
}
