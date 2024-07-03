package com.mobelite.locationvoiture.repository;

import com.mobelite.locationvoiture.dto.CarDto;
import com.mobelite.locationvoiture.model.Car;
import com.mobelite.locationvoiture.model.Reservation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface reservationRepository extends JpaRepository<Reservation, Long> {
    @Modifying
    @Transactional
    @Query("update Reservation r set r.startDate = ?2 where r.id = ?1")
    void updateStartDate(Long reservationId, LocalDateTime newStartDate);

    @Modifying
    @Transactional
    @Query("update Reservation r set r.endDate = ?2 where r.id = ?1" )
    void updateEndDate(Long reservationId, LocalDateTime newEndDate);

    @Query("SELECT r.car FROM Reservation r WHERE r.id = ?1")
    Car findCarById(Long reservationId);
}
