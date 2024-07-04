package com.mobelite.locationvoiture.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cars")
@Entity
public class Car {
    @Id
    private Long id;
    private  String carburant;
    private String marque;
    private String modele;
    @Column(name = "prixPN")/*prix par nuit*/
    private BigDecimal fraisLocation;
    private String matricule;
    private String anneemodele;
    private String transmission;
    @ElementCollection
    @CollectionTable(name = "car_photos", joinColumns = @JoinColumn(name = "car_id"))
    @Column(name = "photo")
    private List<byte[]> images = new ArrayList<>();

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

    public List<String> getImagesBase64() {
        return images.stream()
                .map(image -> Base64.getEncoder().encodeToString(image))
                .collect(Collectors.toList());
    }

    public void setImagesBase64(List<String> base64Images) {
        this.images = base64Images.stream()
                .map(Base64.getDecoder()::decode)
                .collect(Collectors.toList());
    }
    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", make='" + marque + '\'' +
                ", model='" + modele + '\'' +
                ", year=" + anneemodele +
                ", licensePlate='" + matricule + '\'' +
                ", fraisLocation=" + fraisLocation +
                ", disponibilite=" + disponibilite +
                ", Carburant=" + carburant +
                ", Transmission=" + transmission +
                ", dateExpAssurance=" + dateExpAssurance +
                ", dateExpVingnette=" + dateExpVingnette +
                ", dateExpVisite=" + dateExpVisite +
                // Exclude reservations to avoid circular reference
                '}';
    }
}
