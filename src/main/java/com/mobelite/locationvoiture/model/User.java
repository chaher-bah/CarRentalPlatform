package com.mobelite.locationvoiture.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data

@Builder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String nom;
    private String prenom;
    @Column(unique = true)
    private String email;

}

