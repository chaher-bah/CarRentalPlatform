package com.mobelite.locationvoiture.service;

import com.mobelite.locationvoiture.model.Client;
import com.mobelite.locationvoiture.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface clientService {
    public void save(Client client);
    public Optional<Client> getClientById(Long id);
    public List<Client> getAllClients();
    public void deleteById(Long id);
    public byte[] getPermisImage(Long id);
    public List<Reservation> getAllReservations(Long clientId);
    public Client getClientByEmail(String email);
    public Client getClientByCin(String cin);
}
