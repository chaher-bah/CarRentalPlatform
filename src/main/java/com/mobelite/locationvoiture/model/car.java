package com.mobelite.locationvoiture.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data

@Entity
@Table(name = "car")
public class car {
    @Id
    private Long id;
    private  String VIN;
    private String marque;
    private String modele;
    @Column(name = "prixPN")/*prix par nuit*/
    private BigDecimal fraisLocation;
    private String matricule;
    @Lob
    private byte[] image;
    private LocalDate dateExpVingnette;
    private LocalDate dateExpVisite;
    private LocalDate dateExpAssurance;
    @Column(name = "dispo")
    private Boolean disponibilite;
}
