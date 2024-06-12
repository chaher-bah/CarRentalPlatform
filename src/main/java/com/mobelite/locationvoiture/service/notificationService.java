package com.mobelite.locationvoiture.service;

import com.mobelite.locationvoiture.model.Notification;

import java.util.List;
import java.util.Optional;

public interface notificationService {
    public void save(Notification notification);
    public void delete(Notification notification);
    public List<Notification> getAll();
    public Optional<Notification> getById(Long id);
    public List<Notification> getByClient(Long clientId);
    public List<Notification> getByAdmin(Long adminId);

}
