package com.mobelite.locationvoiture.dto;

import com.mobelite.locationvoiture.model.Notification;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NotificationDto {
    private Long id;
    private String message;
    private ClientDto client;
    private AdminDto admin;

    //methode pour retourner une dto en donnant une entitee
    public NotificationDto fromEntity(Notification notification) {
        if (notification == null){
            //to do exception
            return null;
        }
        return NotificationDto.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .build();
    }
    //dto---> entity
    public Notification toEntity(NotificationDto notificationDto) {
        if (notificationDto == null){
            return null;
        }
        return Notification.builder()
                .id(notificationDto.getId())
                .message(notificationDto.getMessage())
                .build();
    }
}
