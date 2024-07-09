package com.mobelite.locationvoiture.service;

import com.mobelite.locationvoiture.dto.CarDto;
import com.mobelite.locationvoiture.model.Car;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface CarService {
    Car save(Car car);
    CarDto getCar(Long id);
    List<CarDto> getAllCars();
    List<Car> getAllCarsAdmin();
    List<CarDto> getCarsbymarque(String marque);
    List<CarDto> getCarsByDisponibilite();
    List<CarDto> getCarsByPrice(BigDecimal price);
    void updateDisponibiliteToFalse(Long carId);
    void updateDisponibiliteToTrue(Long carId);
    CarDto updateCarPrice(Long carId, BigDecimal price);
    List<byte[]> getCarImages (Long carId);
    void saveCarImages(Long carId, List<MultipartFile> images);
    byte[] getCarImage(Long carId,int index);
}
