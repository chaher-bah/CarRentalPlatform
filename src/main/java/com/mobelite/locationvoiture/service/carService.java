package com.mobelite.locationvoiture.service;

import com.mobelite.locationvoiture.dto.CarDto;
import com.mobelite.locationvoiture.model.Car;

import java.math.BigDecimal;
import java.util.List;

public interface carService {
    public CarDto save(CarDto car);
    public CarDto getCar(Long id);
    public List<CarDto> getAllCars();
    public void deleteCar(Long carId);
    public List<CarDto> getCarsbymarque(String marque);
    public List<CarDto> getCarsByDisponibilite();
    public List<CarDto> getCarsByPrice(BigDecimal price);
    public void updateDisponibiliteToFalse(Long carId);
    public void updateDisponibiliteToTrue(Long carId);

}
