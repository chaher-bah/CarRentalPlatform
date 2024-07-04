package com.mobelite.locationvoiture.dto;

import com.mobelite.locationvoiture.model.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.mobelite.locationvoiture.utils.constants.APP_ROUTE;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CarDto {
    private Long id;
    private String marque;
    private String modele;
    private BigDecimal fraisLocation;
    private String matricule;
    private String anneemodele;
    private String carburant;
    private String transmission;
    private List<String> imageUrls;
    private Boolean disponibilite;
    private AdminDto admin;

    public static CarDto fromEntity(Car car) {
        if (car == null) {
            // TODO: Handle null car with an exception
            return null;
        }
        return CarDto.builder()
                .id(car.getId())
                .marque(car.getMarque())
                .modele(car.getModele())
                .transmission(car.getTransmission())
                .fraisLocation(car.getFraisLocation())
                .anneemodele(car.getAnneemodele())
                .matricule(car.getMatricule())
                .imageUrls(car.getImages().stream()
                        .map(img -> "http://localhost:2020/"+APP_ROUTE+"/cars/" + car.getId() + "/images/" + (car.getImages().indexOf(img) ))
                        .collect(Collectors.toList()))
                .admin(AdminDto.fromEntity(car.getAdmin()))
                .disponibilite(car.getDisponibilite())
                .carburant(car.getCarburant())
                .build();
    }

    public static Car toEntity(CarDto carDto) {
        if (carDto == null) {
            return null;
        }
        return Car.builder()
                .id(carDto.getId())
                .marque(carDto.getMarque())
                .modele(carDto.getModele())
                .transmission(carDto.getTransmission())
                .fraisLocation(carDto.getFraisLocation())
                .matricule(carDto.getMatricule())
                .images(Collections.emptyList())
                .anneemodele(carDto.getAnneemodele())
                .admin(AdminDto.toEntity(carDto.getAdmin()))
                .disponibilite(carDto.getDisponibilite())
                .carburant(carDto.getCarburant())
                .build();
    }
}