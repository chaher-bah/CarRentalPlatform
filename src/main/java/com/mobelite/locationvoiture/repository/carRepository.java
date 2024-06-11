package com.mobelite.locationvoiture.repository;

import com.mobelite.locationvoiture.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface carRepository extends JpaRepository<Car, Integer> {
    Car findbyMarque(String marque);
}
