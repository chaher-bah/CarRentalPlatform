package com.mobelite.locationvoiture.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Table(name = "cars")
@Entity
public class Car {
    @Id
    private Long id;
    private  String vin;
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
