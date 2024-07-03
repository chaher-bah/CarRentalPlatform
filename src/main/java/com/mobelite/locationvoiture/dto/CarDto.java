//data transfert object
package com.mobelite.locationvoiture.dto;
import com.mobelite.locationvoiture.model.Car;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
@AllArgsConstructor
@Builder
@Data
public class CarDto {
    private Long id;
    private String marque;
    private String modele;
    private BigDecimal fraisLocation;
    private String matricule;
    private String anneemodele;
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
                .anneemodele(car.getAnneemodele())
                .matricule(car.getMatricule())
                .image(car.getImage())
                .admin(AdminDto.fromEntity(car.getAdmin()))
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
                .anneemodele((carDto.getAnneemodele()))
                .admin(AdminDto.toEntity(carDto.getAdmin()))
                .disponibilite(carDto.getDisponibilite())
                .build();
    }
}
