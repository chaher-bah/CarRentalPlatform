package com.mobelite.locationvoiture.service.impl;

import com.mobelite.locationvoiture.dto.CarDto;
import com.mobelite.locationvoiture.exception.EntityNotFoundException;
import com.mobelite.locationvoiture.exception.EntityNotValidException;
import com.mobelite.locationvoiture.exception.ErrorCodes;
import com.mobelite.locationvoiture.model.Car;
import com.mobelite.locationvoiture.model.Client;
import com.mobelite.locationvoiture.repository.carRepository;
import com.mobelite.locationvoiture.service.CarService;
import com.mobelite.locationvoiture.utils.ImageUtils;
import com.mobelite.locationvoiture.validators.CarValidator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CarServiceImpl implements CarService {
    private final carRepository carRepository;

    @Autowired
    public CarServiceImpl(carRepository carRepository) {
        this.carRepository = carRepository;
    }
    @Override
    public CarDto save(CarDto car) {
        List<String> errors = CarValidator.validation(car);
        if (!errors.isEmpty()) {
            log.error("Car Validation errors: {}", errors);
            throw new EntityNotValidException("L'entite voiture n'est pas valide", ErrorCodes.CAR_NOT_VALID,errors);
        }
        return CarDto.fromEntity(carRepository.save(CarDto.toEntity(car)));
    }

    @Override
    public CarDto getCar(Long id) {
        if (id == null){
            log.error("Car id is null cannot get car");
            return null;
        }
        Optional<Car> car = carRepository.findById(id);
        return Optional.of(CarDto.fromEntity(car.get())).orElseThrow(()->
                new EntityNotFoundException("La voiture avec l'ID "+id+"n'esxicte pas dans le BD",ErrorCodes.CAR_NOT_FOUND));
    }

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(CarDto::fromEntity).collect(Collectors.toList());
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
        if (carId == null) {
            log.error("car id is null image cannot be retrieved");
            return null;
        }
        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car not found"));
        if ((car.getImages())==null){
            log.error("Images de voiture n'existe pas");
            return null;
        }
        return car.getImages();
    }
    @Override
    public byte[] getCarImage(Long carId, int imageIndex) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
        if (imageIndex < 0 || imageIndex >= car.getImages().size()) {
            throw new EntityNotFoundException("Image not found");
        }
        return car.getImages().get(imageIndex);
    }
    @Transactional
    @Override
    public void saveCarImages(Long carId, List<MultipartFile> images) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Car not found"));
        if (!images.isEmpty() && images.size() > 5 ){
            throw new EntityNotValidException("L'entite voiture n'est pas valide[photos]");
        }
        try {
            for (MultipartFile imagefile : images) {car.getImages().add(imagefile.getBytes());    }
        } catch (IOException e) {
            log.error("a problem while uploading image");
            throw new RuntimeException(e);
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

}