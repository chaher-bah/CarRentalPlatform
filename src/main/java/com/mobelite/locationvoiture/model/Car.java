package com.mobelite.locationvoiture.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Lob
    @Fetch(FetchMode.JOIN)
    @Column(name = "photo")
    private List<byte[]> imageUrls = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateExpVignette;
    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateExpVisite;
    @JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateExpAssurance;

    @Column(name = "dispo")
    private Boolean disponibilite=true;

    @ManyToOne(targetEntity = Admin.class)
    @JoinColumn(name = "Admin_id")
    @JsonIgnore
    private Admin admin;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
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
                ", fraisLocation=" + fraisLocation +
                ", disponibilite=" + disponibilite +
                ", Carburant=" + carburant +
                ", Transmission=" + transmission +
                ", dateExpAssurance=" + dateExpAssurance +
                ", dateExpVingnette=" + dateExpVignette +
                ", dateExpVisite=" + dateExpVisite +
                '}';
    }
}
