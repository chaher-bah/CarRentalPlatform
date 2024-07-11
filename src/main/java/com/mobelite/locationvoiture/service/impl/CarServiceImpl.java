package com.mobelite.locationvoiture.service.impl;

import com.mobelite.locationvoiture.dto.CarDto;
import com.mobelite.locationvoiture.exception.EntityNotFoundException;
import com.mobelite.locationvoiture.exception.EntityNotValidException;
import com.mobelite.locationvoiture.exception.ErrorCodes;
import com.mobelite.locationvoiture.model.Car;
import com.mobelite.locationvoiture.repository.carRepository;
import com.mobelite.locationvoiture.service.CarService;
import com.mobelite.locationvoiture.validators.CarValidator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

import static com.mobelite.locationvoiture.utils.constants.APP_ROUTE;

@Slf4j
@Service
public class CarServiceImpl implements CarService {
    private final carRepository carRepository;

    @Autowired
    public CarServiceImpl(carRepository carRepository) {
        this.carRepository = carRepository;
    }
    @Override
    public Car save(Car car) {return carRepository.save(car);}

    @Override
    public CarDto getCar(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow();
        return CarDto.fromEntity(car);
    }

    @Override
    public List<CarDto> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream()
                .map(CarDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Car> getAllCarsAdmin() {
        return carRepository.findAll();
    }


    @Override
    public List<CarDto> getCarsbymarque(String marque) {
        if (!StringUtils.hasLength(marque)){
            log.error("Car Marque is null");
            throw new EntityNotValidException("le marque de voiture n'est pas valide",ErrorCodes.CAR_NOT_VALID);
        }
        List<Car> cars =carRepository.findbyMarqueIn(List.of(marque));
        if (cars == null || cars.isEmpty()){
            log.error("No cars with the marque: {}",marque);
            throw new EntityNotFoundException("Il n'exicte pas des voitures avec les marques "+marque,
                    ErrorCodes.CAR_MODEL_NOT_FOUND);
        }
        return cars.stream()
                .map(CarDto::fromEntity)//methode reference
                .collect(Collectors.toList());
    }

    @Override
    public List<CarDto> getCarsByDisponibilite() {
        return carRepository.findByDisponibilite()
                .stream()
                .map(CarDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<CarDto> getCarsByPrice(BigDecimal price) {
        return carRepository.findByPriceLessOrEqual(price)
                .stream()
                .map(CarDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void updateDisponibiliteToFalse(Long carId) {
        if (carId == null){
            log.error("Car id is null cannot update disponibilite to false");
            throw new EntityNotValidException("Car id is null cannot update disponibilite to false",ErrorCodes.CAR_NOT_VALID);
        }
        carRepository.updateDisponibiliteToFalse(carId);
    }

    @Override
    public CarDto updateCarPrice(Long carId, BigDecimal price) {
        if (carId == null){
            log.error("Car id is null cannot update price");
            throw new EntityNotValidException("Car id is null cannot update the price",ErrorCodes.CAR_NOT_VALID);
        }
        CarDto cardto = getCar(carId);
        cardto.setFraisLocation(price);
        return CarDto.fromEntity(carRepository.save(CarDto.toEntity(cardto)));
    }

    @Override
    public List<byte[]> getCarImages(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
        return car.getImageUrls();
    }

    @Override
    public byte[] getCarImage(Long carId, int imageIndex) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
        if (imageIndex < 0 || imageIndex >= car.getImageUrls().size()) {
            throw new EntityNotFoundException("Image not found");
        }
        return car.getImageUrls().get(imageIndex);
    }


    @Transactional
    @Override
    public void saveCarImages(Long carId, List<MultipartFile> images) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
        if (images.size()>6){
            throw new EntityNotValidException("Too many images to save car",ErrorCodes.CAR_IMAGES_EXCEEDED);
        }
        try {
            for (MultipartFile image : images) {
                byte[] imageBytes = image.getBytes();
                car.getImageUrls().add(imageBytes);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save car images", e);
        }
        carRepository.save(car);
    }

    @Override
    public void updateDisponibiliteToTrue(Long carId) {
        if (carId == null){
            log.error("Car id is null cannot update disponibilite to true");
            throw new EntityNotValidException("Car id is null cannot update disponibilite to true",ErrorCodes.CAR_NOT_VALID);
        }
        /*CarDto cardto = getCar(carId);
        *cardto.setDisponibilite(true);
        *carRepository.save(CarDto.toEntity(cardto));*/
        carRepository.updateDisponibiliteToTrue(carId);
    }
    @Override
    public void deleteCar(Long carId){
        if (carId == null) {
            log.error("Car id is null cannot delete car");
            throw new EntityNotValidException("Car id is null cannot delete car", ErrorCodes.CAR_NOT_VALID);
        }
        carRepository.deleteById(carId);
    }
    @Override
    public Car updateCar(Long id, Car car, List<MultipartFile> images) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + id, ErrorCodes.CAR_NOT_FOUND));

        existingCar.setMarque(car.getMarque());
        existingCar.setModele(car.getModele());
        existingCar.setAnneemodele(car.getAnneemodele());
        existingCar.setCarburant(car.getCarburant());
        existingCar.setMatricule(car.getMatricule());
        existingCar.setTransmission(car.getTransmission());
        existingCar.setDateExpAssurance(car.getDateExpAssurance());
        existingCar.setDateExpVignette(car.getDateExpVignette());
        existingCar.setDateExpVisite(car.getDateExpVisite());
        existingCar.setFraisLocation(car.getFraisLocation());

        if (images != null && !images.isEmpty()) {
            List<byte[]> imageBytesList = images.stream()
                    .map(image -> {
                        try {
                            return image.getBytes();
                        } catch (IOException e) {
                            throw new EntityNotValidException("Failed to read image bytes", e);
                        }
                    })
                    .collect(Collectors.toList());
            existingCar.setImageUrls(imageBytesList);
        }

        return carRepository.save(existingCar);
    }
}