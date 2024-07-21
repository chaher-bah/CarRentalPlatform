package com.mobelite.locationvoiture.validators;

import com.mobelite.locationvoiture.dto.ClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    public static List<String> validation(ClientDto clientDto){
        List<String> errors = new ArrayList<String>();
        if(clientDto == null){
            errors.add("Client n'est pas valide");
            errors.add("Nom n'est pas valide");
            errors.add("Prenom n'est pas valide");
            errors.add("Cin n'est pas valide");
            errors.add("Numero de Tel n'est pas valide");
        }
        assert (clientDto != null);
        if (!StringUtils.hasText(clientDto.getNom())){
            errors.add("Nom n'est pas valide");
        }
        if (!StringUtils.hasText(clientDto.getPrenom())){
            errors.add("Prenom n'est pas valide");
        }
        if (!StringUtils.hasText(clientDto.getCin())){
            errors.add("Cin n'est pas valide");
        } else if (clientDto.getCin().length()< 8){
            errors.add("Cin doit etre au moins 8 caracteres");
        }
        if (!StringUtils.hasText(clientDto.getNumTel())){
            errors.add("Numero de Tel n'est pas valide");
        } else if (!(clientDto.getNumTel().chars().allMatch((c -> Character.isDigit(c) || c == '+')))){
            errors.add("Numero de Tel doit etre numerique ");
        }

        return errors;
    }
}
