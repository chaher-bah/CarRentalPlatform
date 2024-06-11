package com.mobelite.locationvoiture.service.impl;

import com.mobelite.locationvoiture.model.Car;
import com.mobelite.locationvoiture.repository.carRepository;
import com.mobelite.locationvoiture.service.carService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@Service
public class carServiceImpl implements carService {
    private final carRepository carRepository;

    @Autowired
    public carServiceImpl(carRepository carRepository) {
        carRepository = carRepository;
    }
    @Override
    public void save(Car car) {

    }

    @Override
    public Optional<Car> getCar(int id) {
        return Optional.empty();
    }

    @Override
    public List<Car> getAllCars() {
        return List.of();
    }

    @Override
    public void deleteCar(Car car) {

    }

    @Override
    public List<Car> getCarsbymarque(String marque) {
        return List.of(carRepository.findbyMarque(marque));
    }

    @Override
    public List<Car> getCarsByAvailability() {
        return List.of();
    }

    @Override
    public List<Car> getCarsByPrice(BigDecimal price) {
        return List.of();
    }
}