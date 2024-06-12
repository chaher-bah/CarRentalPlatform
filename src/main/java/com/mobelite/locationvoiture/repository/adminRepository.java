package com.mobelite.locationvoiture.repository;

import com.mobelite.locationvoiture.model.Admin;
import com.mobelite.locationvoiture.model.Car;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface adminRepository extends JpaRepository<Admin, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Admin a set a.Cars =?2 where a.id=?1")
    void updateCars(Long adminId,List<Car> cars);
}
