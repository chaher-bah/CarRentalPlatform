package com.mobelite.locationvoiture.dto;

import com.mobelite.locationvoiture.model.Admin;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class AdminDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String localisation;
    private List<CarDto> Cars;
    private List<NotificationDto> notifications;
    public static AdminDto fromEntity(Admin admin) {
        if (admin == null){
            //to do exception
            return null;
        }
        return AdminDto.builder()
                .id(admin.getId())
                .nom(admin.getNom())
                .prenom(admin.getPrenom())
                .email(admin.getEmail())
                .localisation(admin.getLocalisation())
                .build();
    }
    //dto---> entity
    public static Admin toEntity(AdminDto AdminDto) {
        if (AdminDto == null){
            return null;
        }
        Admin admin = new Admin();
        admin.setId(AdminDto.getId());
        admin.setNom(AdminDto.getNom());
        admin.setPrenom(AdminDto.getPrenom());
        admin.setEmail(AdminDto.getEmail());
        admin.setLocalisation(AdminDto.getLocalisation());
        return admin;
    }
}
