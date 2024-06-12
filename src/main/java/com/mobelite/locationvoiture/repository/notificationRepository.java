package com.mobelite.locationvoiture.repository;

import com.mobelite.locationvoiture.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface notificationRepository extends JpaRepository<Notification, Long> {
    @Query("select n from Notification n where n.client.id = ?1")
    List<Notification> findByClientId(Long clientId);

    @Query("select n from Notification n where n.admin.id = ?1")
    List<Notification> findByAdminId(Long adminId);
}
