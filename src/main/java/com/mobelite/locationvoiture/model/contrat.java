package com.mobelite.locationvoiture.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;
@Data

@Entity
@Table
public class contrat {
    @Id
    private Long id;

    //reservation reservation;


}
