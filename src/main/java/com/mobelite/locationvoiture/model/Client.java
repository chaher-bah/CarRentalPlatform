package com.mobelite.locationvoiture.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "clients")
public class Client extends User {
    private String cin;
    private String numTel;
    @OneToMany(mappedBy = "client")
    private List<Reservation> reservations;
    @Lob
    private byte[] photoPermis;

}
