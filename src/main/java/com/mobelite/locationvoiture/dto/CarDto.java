//data transfert object
package com.mobelite.locationvoiture.dto;
import com.mobelite.locationvoiture.model.Car;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
@Builder
@Data
public class CarDto {
    private Long id;
    private String marque;
    private String modele;
    private BigDecimal fraisLocation;
    private String matricule;
    @Lob
    private byte[] image;
   private Boolean disponibilite;
   private AdminDto admin;
   public static CarDto fromEntity(Car car) {
        if (car == null){
            //to do exception
            return null;
        }
        return CarDto.builder()
                .id(car.getId())
                .marque(car.getMarque())
                .modele(car.getModele())
                .fraisLocation(car.getFraisLocation())
                .matricule(car.getMatricule())
                .image(car.getImage())
                .disponibilite(car.getDisponibilite())
                .build();
    }
    //dto---> entity
    public static Car toEntity(CarDto carDto) {
        if (carDto == null){
            return null;
        }
        return Car.builder()
                .id(carDto.getId())
                .marque(carDto.getMarque())
                .modele(carDto.getModele())
                .fraisLocation(carDto.getFraisLocation())
                .matricule(carDto.getMatricule())
                .image(carDto.getImage())
                .disponibilite(carDto.getDisponibilite())
                .build();
    }
}
