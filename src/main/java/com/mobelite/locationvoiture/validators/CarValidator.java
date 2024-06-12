package com.mobelite.locationvoiture.validators;

import com.mobelite.locationvoiture.dto.CarDto;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CarValidator {
    public static List<String> validation(CarDto carDto) {
        List<String> errors = new ArrayList<String>();
        if (carDto == null) {
            errors.add("Voiture n'est pas valide");
            errors.add("Marque n'est pas valide");
            errors.add("Modele n'est pas valide");
            errors.add("Matricule n'est pas valide");
            errors.add("Frais de location n'est pas valide");
        }
        assert carDto != null;
        if (!StringUtils.hasText(carDto.getMarque())) {
            errors.add("Marque n'est pas valide");
        }
        if (!StringUtils.hasText(carDto.getModele())) {
            errors.add("modele n'est pas valide");
        }
        if (!StringUtils.hasText(carDto.getMatricule())) {
            errors.add("Matricule n'est pas valide");
        }
        if (carDto.getFraisLocation() == null || carDto.getFraisLocation().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("Frais de Location n'est pas valide");
        }
        return errors;
    }
}
