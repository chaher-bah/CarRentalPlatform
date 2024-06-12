package com.mobelite.locationvoiture.validators;

import com.mobelite.locationvoiture.dto.AdminDto;
import com.mobelite.locationvoiture.dto.ClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AdminValidator {
    public static List<String> validation(AdminDto adminDto) {
        List<String> errors = new ArrayList<String>();
        if (adminDto == null) {
            errors.add("Admin n'est pas valide");
            errors.add("Nom n'est pas valide");
            errors.add("Prenom n'est pas valide");
            errors.add("Email n'est pas valide");
        }
        assert adminDto != null;
        if (!StringUtils.hasText(adminDto.getNom())){
            errors.add("Nom n'est pas valide");
        }
        if (!StringUtils.hasText(adminDto.getPrenom())) {
            errors.add("Prenom n'est pas valide");
        }
        if (!StringUtils.hasText(adminDto.getEmail())){
            errors.add("Email n'est pas valide");
        }

        return errors;
    }
}
