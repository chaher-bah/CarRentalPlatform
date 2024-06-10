package com.mobelite.locationvoiture.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data


@Entity
@Table
public class admin extends user{
    private String localisation;
    @OneToMany
    private List<car> cars;
}
