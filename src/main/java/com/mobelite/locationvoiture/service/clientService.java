package com.mobelite.locationvoiture.service;

import com.mobelite.locationvoiture.dto.ClientDto;
import com.mobelite.locationvoiture.dto.ReservationDto;
import com.mobelite.locationvoiture.model.Client;
import com.mobelite.locationvoiture.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface clientService {
    public ClientDto save(ClientDto client);
    public ClientDto getClientById(Long id);
    public List<ClientDto> getAllClients();
    public void deleteById(Long id);
    public byte[] getPermisImage(Long id);
    public List<ReservationDto> getAllReservations(Long clientId);
    public ClientDto getClientByEmail(String email);
    public ClientDto getClientByCin(String cin);
}
