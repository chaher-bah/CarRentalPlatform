package com.mobelite.locationvoiture.repository;

import com.mobelite.locationvoiture.model.Car;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface carRepository extends JpaRepository<Car, Long> {
    @Query("SELECT c from Car c where c.marque in ?1")
    List<Car> findbyMarqueIn(List<String> marques);

    @Query("select c from Car c where c.disponibilite =true")
    List<Car> findByDisponibilite();

    @Query("select c from Car c where c.fraisLocation <= ?1")
    List<Car> findByPriceLessOrEqual(BigDecimal price);

    @Modifying
    @Transactional
    @Query("UPDATE Car c SET c.disponibilite = true WHERE c.id = ?1")
    void updateDisponibiliteToTrue(Car car);

    @Modifying
    @Transactional
    @Query("UPDATE Car c SET c.disponibilite = false WHERE c.id = ?1")
    void updateDisponibiliteToFalse(Car car);
}
