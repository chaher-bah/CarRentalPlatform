package com.mobelite.locationvoiture.service;

import com.mobelite.locationvoiture.model.Car;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface carService {
    public void save(Car car);
    public Optional<Car> getCar(int id);
    public List<Car> getAllCars();
    public void deleteCar(Car car);
    public List<Car> getCarsbymarque(String marque);
    public List<Car> getCarsByAvailability();
    public List<Car> getCarsByPrice(BigDecimal price);


}
