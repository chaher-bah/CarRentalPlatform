package com.mobelite.locationvoiture.service;

import com.mobelite.locationvoiture.dto.NotificationDto;
import com.mobelite.locationvoiture.model.Notification;

import java.util.List;
import java.util.Optional;

public interface notificationService {
    public NotificationDto save(NotificationDto notification);
    public void delete(Long notificationId);
    public List<NotificationDto> getAll();
    public NotificationDto getById(Long id);
    public List<NotificationDto> getByClient(Long clientId);
    public List<NotificationDto> getByAdmin(Long adminId);

}
