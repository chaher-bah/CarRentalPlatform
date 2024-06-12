package com.mobelite.locationvoiture.service.impl;

import com.mobelite.locationvoiture.model.Client;
import com.mobelite.locationvoiture.model.Reservation;
import com.mobelite.locationvoiture.repository.clientRepository;
import com.mobelite.locationvoiture.service.clientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class clientServiceImpl implements clientService {
    private final clientRepository clientRepository;

    @Autowired
    public clientServiceImpl(clientRepository clientRepository){
        this.clientRepository = clientRepository;
    }
    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }

    @Override
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public byte[] getPermisImage(Long id) {
        return clientRepository.findById(id).get().getPhotoPermis();
    }

    @Override
    public List<Reservation> getAllReservations(Long clientId) {
        return clientRepository.findById(clientId).get().getReservations();
    }

    @Override
    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public Client getClientByCin(String cin) {
        return clientRepository.findByCin(cin);
    }
}
