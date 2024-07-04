package com.mobelite.locationvoiture.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client extends User {
    private String cin;
    private String numTel;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Reservation> reservations=new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "client_photos", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "photo")
    private List<byte[]> photoPermis = new ArrayList<>();


    @OneToMany(targetEntity = Notification.class)
    private List<Notification> notifications;

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setClient(this);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.setClient(null);
    }
    @Override
    public String toString() {
        return "Client{" +
                "id=" + getId() +
                ", cin='" + cin + '\'' +
                ", numTel='" + numTel + '\'' +
                '}';
    }


}
