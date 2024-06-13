package com.mobelite.locationvoiture.service;

import com.mobelite.locationvoiture.dto.ClientDto;
import com.mobelite.locationvoiture.dto.ReservationDto;

import java.util.List;

public interface ClientService {
    ClientDto save(ClientDto client);
    ClientDto getClientById(Long id);
    List<ClientDto> getAllClients();
    void deleteById(Long id);
    byte[] getPermisImage(Long id);
    List<ReservationDto> getAllReservations(Long clientId);
    ClientDto getClientByEmail(String email);
    ClientDto getClientByCin(String cin);
}
