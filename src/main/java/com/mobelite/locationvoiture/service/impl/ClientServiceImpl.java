package com.mobelite.locationvoiture.service.impl;

import com.mobelite.locationvoiture.dto.ClientDto;
import com.mobelite.locationvoiture.dto.ReservationDto;
import com.mobelite.locationvoiture.exception.EntityNotFoundException;
import com.mobelite.locationvoiture.exception.EntityNotValidException;
import com.mobelite.locationvoiture.exception.ErrorCodes;
import com.mobelite.locationvoiture.model.Car;
import com.mobelite.locationvoiture.model.Client;
import com.mobelite.locationvoiture.model.Reservation;
import com.mobelite.locationvoiture.repository.clientRepository;
import com.mobelite.locationvoiture.service.ClientService;
import com.mobelite.locationvoiture.utils.ImageUtils;
import com.mobelite.locationvoiture.validators.ClientValidator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {
    private final clientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(clientRepository clientRepository){
        this.clientRepository = clientRepository;
    }
    @Override
    public ClientDto save(ClientDto client) {
        List<String> errors = ClientValidator.validation(client);
        if (!errors.isEmpty()) {
            log.error("client Validation errors: {}", errors);
            throw new EntityNotValidException("L'entite Client n'est pas valide", ErrorCodes.CLIENT_NOT_VALID,errors);
        }

        return ClientDto.fromEntity(clientRepository.save(ClientDto.toEntity(client)));
    }
    @Override
    public Client add (Client client) {return clientRepository.save(client);}




    @Override
    public ClientDto getClientById(Long id) {
        if (id == null){
            log.error("Client id is null cannot get client by id");
            return null;
        }
        Optional<Client> client = clientRepository.findById(id);
        return Optional.of(ClientDto.fromEntity(client.get())).orElseThrow(()->
                new EntityNotFoundException("La Client avec l'ID "+id+"n'esxicte pas dans le BD",ErrorCodes.CLIENT_NOT_FOUND));
    }

    @Override
    public List<ClientDto> getAllClients() {
        List<Client> clientsfound=clientRepository.findAll();
        return clientsfound
                .stream()
                .map(ClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        if (id == null){
            log.error("Client id is null client cannot be deleted");
            return;
        }
        Client client=clientRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("La Client n'est pas valide",ErrorCodes.CLIENT_NOT_FOUND));
        clientRepository.deleteById(client.getId());
    }

    @Override
    public byte[] getPermisImage(Long id,int imgIndex) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        if (imgIndex < 0 || imgIndex >= client.getPhotoPermis().size()) {
            throw new EntityNotFoundException("Image not found");
        }
        return client.getPhotoPermis().get(imgIndex);
    }

    @Override
    public List<byte[]> getPermisImages(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        return client.getPhotoPermis();
    }

    @Override
    public List<ReservationDto> getAllReservations(Long clientId) {
        if (clientId == null){
            log.error("Client id is null cannot get reservations");
            return null;
        }
        List<Reservation> reservations=clientRepository.findById(clientId).get().getReservations();
        return reservations.stream().map(ReservationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public ClientDto getClientByEmail(String email) {
        if (!StringUtils.hasLength(email)){
            log.error("Client email is null");
            return null;
        }
        Client clientFound = clientRepository.findByEmail(email);
        if (clientFound == null) {
            throw new EntityNotFoundException("L'entite Client avec l'email " + email + " n'exicte pas dans le BD", ErrorCodes.CLIENT_NOT_FOUND);
        }

        return ClientDto.fromEntity(clientFound);
    }

    @Override
    public List<ClientDto> getClientByCin(String cin) {
        if (!StringUtils.hasLength(cin)){
            log.error("Client CIN is null");
            return null;
        }
        List<Client> clients = clientRepository.findByCin(cin);
        if (clients.isEmpty()) {
            throw new EntityNotFoundException("L'entite Client avec le CIN " + cin + " n'exicte pas dans le BD", ErrorCodes.CLIENT_NOT_FOUND);
        }

        return clients.stream()
                .map(ClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void savePermisImage(Long id, List<MultipartFile> imagefiles) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
        if (!imagefiles.isEmpty() && imagefiles.size() > 4 ){
            throw new EntityNotValidException("L'entite Client n'est pas valide [check photos/don't exceed 4]");
        }
        try {
            for (MultipartFile image : imagefiles) {
                byte[] imageBytes = image.getBytes();
                client.getPhotoPermis().add(imageBytes);
            }
        } catch (IOException e) {
            log.error("a problem while uploading image");
            throw new RuntimeException(e);
        }
        clientRepository.save(client);
    }

    @Override
    public List<ClientDto> getClientByNomOrPrenom(String nom, String prenom) {
        if (!StringUtils.hasLength(nom) && !StringUtils.hasLength(prenom)){
            log.error("Client nom and prenom are null");
            return null;
        }
        List<Client> clients = clientRepository.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(nom, prenom);
        if (clients.isEmpty()) {
            throw new EntityNotFoundException("L'entite Client avec le nom/prenom " + nom + "/" + prenom + " n'exicte pas dans le BD", ErrorCodes.CLIENT_NOT_FOUND);
        }

        return clients.stream()
                .map(ClientDto::fromEntity)
                .collect(Collectors.toList());
    }
    @Override
    public Client update(Long id, Client client, List<MultipartFile> imagefiles) {
        Client exictingClient= (clientRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Client with id"+id+"not found", ErrorCodes.CLIENT_NOT_FOUND)));
        exictingClient.setNom(client.getNom());
        exictingClient.setPrenom(client.getPrenom());
        exictingClient.setEmail(client.getEmail());
        exictingClient.setCin(client.getCin());
        exictingClient.setReservations(client.getReservations());
        if (imagefiles != null && !imagefiles.isEmpty()) {
            List<byte[]> imageBytesList = imagefiles.stream()
                    .map(image -> {
                        try {
                            return image.getBytes();
                        } catch (IOException e) {
                            throw new EntityNotValidException("Failed to read image bytes", e);
                        }
                    })
                    .collect(Collectors.toList());
            exictingClient.setPhotoPermis(imageBytesList);
        }

        return clientRepository.save(exictingClient);
    }

    @Override
    public boolean clientExistsByEmail(String email) {
        return clientRepository.findByEmail(email) !=null;
    }
}
