package com.mobelite.locationvoiture.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;
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
    @JsonIgnore
    @ToString.Exclude
    private List<Reservation> reservations=new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "client_photos", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "photo")
    @Lob
    private List<byte[]> photoPermis = new ArrayList<>();


    @OneToMany(targetEntity = Notification.class)
    @JsonIgnore
    @ToString.Exclude
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
