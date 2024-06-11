package com.mobelite.locationvoiture.repository;

import com.mobelite.locationvoiture.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface notificationRepository extends JpaRepository<Notification, Integer> {
}
