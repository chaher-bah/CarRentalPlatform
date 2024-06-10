package com.mobelite.locationvoiture.model;

import jakarta.persistence.*;
import lombok.*;

@Data

@Entity
@Table
public class notification {
    @Id
    private Long id;
    private String message;
    @ManyToOne
    @JoinColumn(name = "userId")
    private user user;
}
