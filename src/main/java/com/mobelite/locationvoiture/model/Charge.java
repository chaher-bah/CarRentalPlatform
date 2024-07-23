package com.mobelite.locationvoiture.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class Charge {
    private String id;
    private String label;
    private BigDecimal charge;
    public Charge(String label, BigDecimal charge) {
        this.id = RandomStringUtils.randomAlphabetic(10); // Generate a unique ID
        this.label = label;
        this.charge = charge;
    }
}
