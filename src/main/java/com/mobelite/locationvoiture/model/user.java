package com.mobelite.locationvoiture.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table
public class user {
    @Id
    @GeneratedValue
    private Long id;
    private String nom;
    private String prenom;
    private String email;

}

