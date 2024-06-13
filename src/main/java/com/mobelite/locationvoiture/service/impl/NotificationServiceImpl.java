package com.mobelite.locationvoiture.service.impl;

import com.mobelite.locationvoiture.dto.NotificationDto;
import com.mobelite.locationvoiture.exception.EntityNotFoundException;
import com.mobelite.locationvoiture.exception.EntityNotValidException;
import com.mobelite.locationvoiture.exception.ErrorCodes;
import com.mobelite.locationvoiture.repository.notificationRepository;
import com.mobelite.locationvoiture.model.Notification;
import com.mobelite.locationvoiture.service.NotificationService;
import com.mobelite.locationvoiture.validators.NotificationValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {
    private final notificationRepository notificationRepository;
    @Autowired
    public NotificationServiceImpl(notificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }

    @Override
    public NotificationDto save(NotificationDto notification) {
        List<String> errors = NotificationValidator.validation(notification);
        if (!errors.isEmpty()) {
            log.error("Car Validation errors: {}", errors);
            throw new EntityNotValidException("L'entite notification n'est pas valide", ErrorCodes.NOTIFICATION_NOT_VALID,errors);
        }
        return NotificationDto.fromEntity(notificationRepository.save(NotificationDto.toEntity(notification)));
    }

    @Override
    public void delete(Long notificationId){
        if (notificationId == null){
            log.error("Notification id is null cannot delete it");
            return;
        }
        notificationRepository.deleteById(notificationId);
    }

    @Override
    public List<NotificationDto> getAll() {
        List<Notification> notifs= notificationRepository.findAll();
        return  notifs.stream().map(NotificationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public NotificationDto getById(Long id) {
        if (id == null){
            log.error("Notification id is null cannot get it");
            return null;
        }
        Optional<Notification> notifs=  notificationRepository.findById(id);
        return Optional.of(NotificationDto.fromEntity(notifs.get())).orElseThrow(()->
                new EntityNotFoundException("La Notification avec l'ID "+id+"n'esxicte pas dans le BD",ErrorCodes.NOTIFICATION_NOT_FOUND));
    }

    @Override
    public List<NotificationDto> getByClient(Long clientId) {
        List<Notification> notifsperclient=notificationRepository.findByClientId(clientId);
        if (notifsperclient == null){ throw new EntityNotFoundException("La Notification avec le Client Id"
                +clientId+"n'exicte pas dans le BD",ErrorCodes.NOTIFICATION_NOT_FOUND); }

        return notifsperclient.stream().map(NotificationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<NotificationDto> getByAdmin(Long adminId) {
        List<Notification> notifsperadmin=notificationRepository.findByClientId(adminId);
        if (notifsperadmin == null){ throw new EntityNotFoundException("La Notification avec l'admin Id"
                +adminId+"n'exicte pas dans le BD",ErrorCodes.NOTIFICATION_NOT_FOUND); }

        return notifsperadmin.stream().map(NotificationDto::fromEntity).collect(Collectors.toList());
    }
}
