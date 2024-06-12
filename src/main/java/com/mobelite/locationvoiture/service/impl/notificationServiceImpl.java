package com.mobelite.locationvoiture.service.impl;

import com.mobelite.locationvoiture.repository.notificationRepository;
import com.mobelite.locationvoiture.model.Notification;
import com.mobelite.locationvoiture.service.notificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class notificationServiceImpl implements notificationService {
    private final notificationRepository notificationRepository;
    @Autowired
    public notificationServiceImpl(notificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void save(Notification notification) {
        notificationRepository.save(notification);
    }

    @Override
    public void delete(Notification notification){
        notificationRepository.delete(notification);
    }

    @Override
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Optional<Notification> getById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public List<Notification> getByClient(Long clientId) {
        return notificationRepository.findByClientId(clientId);
    }

    @Override
    public List<Notification> getByAdmin(Long adminId) {
        return notificationRepository.findByAdminId(adminId);
    }
}
