package com.mobelite.locationvoiture.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data

@Entity
@Table
public class client extends user{
    private String CIN;
    private int numTel;
    @OneToMany(mappedBy = "client")
    private List<reservation> reservations;
    @Lob
    private byte[] photoPermis;

}
