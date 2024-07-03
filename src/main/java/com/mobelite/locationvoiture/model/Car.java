package com.mobelite.locationvoiture.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private String anneemodele;
    @Lob
    private byte[] image;
    private LocalDate dateExpVingnette;
    private LocalDate dateExpVisite;
    private LocalDate dateExpAssurance;
    @Column(name = "dispo")
    private Boolean disponibilite;
    @ManyToOne(targetEntity = Admin.class)
    @JoinColumn(name = "Admin_id")
    private Admin admin;
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Reservation> reservations=new ArrayList<>();


    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setCar(this);
    }
    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", make='" + marque + '\'' +
                ", model='" + modele + '\'' +
                ", year=" + anneemodele +
                ", licensePlate='" + matricule + '\'' +
                ", image='" + image + '\'' +
                ", fraisLocation=" + fraisLocation +
                ", disponibilite=" + disponibilite +
                ", vin=" + vin +
                ", dateExpAssurance=" + dateExpAssurance +
                ", dateExpVingnette=" + dateExpVingnette +
                ", dateExpVisite=" + dateExpVisite +
                // Exclude reservations to avoid circular reference
                '}';
    }
}
