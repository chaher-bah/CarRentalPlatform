package com.mobelite.locationvoiture.service;

import com.mobelite.locationvoiture.dto.CarDto;

import java.math.BigDecimal;
import java.util.List;

public interface CarService {
    CarDto save(CarDto car);
    CarDto getCar(Long id);
    List<CarDto> getAllCars();
    void deleteCar(Long carId);
    List<CarDto> getCarsbymarque(String marque);
    List<CarDto> getCarsByDisponibilite();
    List<CarDto> getCarsByPrice(BigDecimal price);
    void updateDisponibiliteToFalse(Long carId);
    void updateDisponibiliteToTrue(Long carId);
    CarDto updateCarPrice(Long carId, BigDecimal price);
}
