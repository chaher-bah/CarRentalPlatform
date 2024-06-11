package com.mobelite.locationvoiture.dto;

import com.mobelite.locationvoiture.model.Client;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClientDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String cin;
    private String numTel;

    private List<ReservationDto> reservations;
    @Lob
    private byte[] photoPermis;

    private List<NotificationDto> notifications;
    public ClientDto fromEntity(Client client) {
        if (client == null){
            //to do exception
            return null;
        }
        return ClientDto.builder()
                .id(client.getId())
                .nom(client.getNom())
                .prenom(client.getPrenom())
                .email(client.getEmail())
                .cin(client.getCin())
                .numTel(client.getNumTel())
                .photoPermis(client.getPhotoPermis())
                .build();
    }
    //dto---> entity
    public Client toEntity(ClientDto clientDto) {
        if (clientDto == null){
            return null;
        }
        Client client = new Client();
        client.setId(clientDto.getId());
        client.setNom(clientDto.getNom());
        client.setPrenom(clientDto.getPrenom());
        client.setEmail(clientDto.getEmail());
        client.setCin(clientDto.getCin());
        client.setNumTel(clientDto.getNumTel());
        client.setPhotoPermis(clientDto.getPhotoPermis());
        return client;
    }
}
