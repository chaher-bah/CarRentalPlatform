package com.mobelite.locationvoiture.service.impl;

import com.mobelite.locationvoiture.dto.ClientDto;
import com.mobelite.locationvoiture.dto.ReservationDto;
import com.mobelite.locationvoiture.exception.EntityNotFoundException;
import com.mobelite.locationvoiture.exception.EntityNotValidException;
import com.mobelite.locationvoiture.exception.ErrorCodes;
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
            log.error("Car Validation errors: {}", errors);
            throw new EntityNotValidException("L'entite Client n'est pas valide", ErrorCodes.CLIENT_NOT_VALID,errors);
        }
        return ClientDto.fromEntity(clientRepository.save(ClientDto.toEntity(client)));
    }

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
        clientRepository.deleteById(id);
    }

    @Override
    public List<byte[]> getPermisImage(Long id) {
        if (id == null) {
            log.error("client id is null image cannot be retrieved");
            return null;
        }
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
        if ((client.getPhotoPermis())==null){
            log.error("Image permis n'existe pas");
            return null;
        }
        return ImageUtils.decompressImages(client.getPhotoPermis());
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
        Optional<Client> client = Optional.ofNullable(clientRepository.findByEmail(email));
        return Optional.of(ClientDto.fromEntity(client.get())).orElseThrow(()->
                new EntityNotFoundException("L'entite Client avec l'eamil"+email+"n'exicte pas dans le BD", ErrorCodes.CLIENT_NOT_FOUND));
    }

    @Override
    public ClientDto getClientByCin(String cin) {
        if (!StringUtils.hasLength(cin)){
            log.error("Client CIN is null");
            return null;
        }
        Optional<Client> client = Optional.ofNullable(clientRepository.findByCin(cin));
        return Optional.of(ClientDto.fromEntity(client.get())).orElseThrow(()->
                new EntityNotFoundException("L'entite Client avec le CIN  "+cin+"n'exicte pas dans le BD", ErrorCodes.CLIENT_NOT_FOUND));

    }

    @Override
    @Transactional
    public void savePermisImage(Long id, List<MultipartFile> imagefiles) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
        if (!imagefiles.isEmpty() && imagefiles.size() > 5 ){
            throw new EntityNotValidException("L'entite Client n'est pas valide [check photos/don't exceed 5]");
        }
        try {
            for (MultipartFile imagefile : imagefiles) {client.getPhotoPermis().add(ImageUtils.compressImage(imagefile.getBytes()));    }
        } catch (IOException e) {
            log.error("a problem while uploading image");
            throw new RuntimeException(e);
        }
        clientRepository.save(client);
    }
}
