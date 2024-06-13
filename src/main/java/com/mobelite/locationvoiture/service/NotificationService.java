package com.mobelite.locationvoiture.service;

import com.mobelite.locationvoiture.dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    NotificationDto save(NotificationDto notification);
    void delete(Long notificationId);
    List<NotificationDto> getAll();
    NotificationDto getById(Long id);
    List<NotificationDto> getByClient(Long clientId);
    List<NotificationDto> getByAdmin(Long adminId);

}
