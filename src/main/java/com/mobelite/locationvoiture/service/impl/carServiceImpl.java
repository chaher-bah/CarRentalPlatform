package com.mobelite.locationvoiture.service.impl;

import com.mobelite.locationvoiture.model.Car;
import com.mobelite.locationvoiture.repository.carRepository;
import com.mobelite.locationvoiture.service.carService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@Data
@Service
public class carServiceImpl implements carService {
    private final carRepository carRepository;

    @Autowired
    public carServiceImpl(carRepository carRepository) {
        this.carRepository = carRepository;
    }
    @Override
    public void save(Car car) {
        //TO DO exception handeling
        carRepository.save(car);
    }

    @Override
    public Optional<Car> getCar(Long id) {
        return carRepository.findById(id);
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public void deleteCar(Car car) {
        //to do exception handling
        carRepository.delete(car);
    }

    @Override
    public List<Car> getCarsbymarque(String marque) {
        return carRepository.findbyMarqueIn(List.of(marque));
    }

    @Override
    public List<Car> getCarsByDisponibilite() {
        return carRepository.findByDisponibilite();
    }

    @Override
    public List<Car> getCarsByPrice(BigDecimal price) {
        return carRepository.findByPriceLessOrEqual(price);
    }

    @Override
    public void updateDisponibiliteToFalse(Car car) {
        carRepository.updateDisponibiliteToFalse(car);
    }

    @Override
    public void updateDisponibiliteToTrue(Car car) {
        carRepository.updateDisponibiliteToTrue(car);
    }

}