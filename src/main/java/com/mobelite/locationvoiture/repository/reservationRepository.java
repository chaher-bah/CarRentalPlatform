package com.mobelite.locationvoiture.repository;

import com.mobelite.locationvoiture.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface reservationRepository extends JpaRepository<Reservation, Long> {
}
