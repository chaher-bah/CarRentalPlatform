package com.mobelite.locationvoiture.validators;

import com.mobelite.locationvoiture.dto.ReservationDto;

import java.util.ArrayList;
import java.util.List;

public class ReservationValidator {
    public static List<String> validation(ReservationDto reservationDto) {
        List<String> errors = new ArrayList<String>();
        if (reservationDto == null) {
            errors.add("Client n'est pas valide");
            errors.add("Voiture n'est pas valide");
            errors.add("Date de debut de location n'est pas valide");
            errors.add("Date de fin de location pas valide");
        }
        assert reservationDto!= null;
        if (reservationDto.getClient()== null) {
            errors.add("Client n'est pas valide");
        }
        if (reservationDto.getCar() == null) {
            errors.add("Voiture n'est pas valide");
        }
        if (reservationDto.getStartDate() == null) {
            errors.add("Date de debut de location n'est pas valide");
        }
        if (reservationDto.getEndDate() == null) {
            errors.add("Date de fin de location n'est pas valide");
        }if (reservationDto.getStartDate() !=null && reservationDto.getEndDate() != null && !reservationDto.getStartDate().isBefore(reservationDto.getEndDate())) {
            errors.add("Le date de debut de location doit etre avant la date du fin");
        }
        return errors;
    }
}
