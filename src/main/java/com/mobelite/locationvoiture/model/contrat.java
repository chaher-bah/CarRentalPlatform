package com.mobelite.locationvoiture.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;
@Data

@Entity
@Table
public class contrat {
    private Long id;
    private Date startDate;
    private Date endDate;
    

}
