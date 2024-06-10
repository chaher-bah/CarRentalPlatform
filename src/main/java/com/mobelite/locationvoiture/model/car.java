package com.mobelite.locationvoiture.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Data

@Entity
@Table
public class car {
    @Id
    private Long id;
    private  String VIN;
    private String marque;
    private String modele;
    private String matricule;
    @Lob
    private byte[] image;
    private LocalDate dateExpVingnette;
    private LocalDate dateExpVisite;
    private LocalDate dateExpAssurance;
    private Boolean disponibilite;
}
