package com.mobelite.locationvoiture.dto;

import com.mobelite.locationvoiture.model.Admin;
import com.mobelite.locationvoiture.model.Client;
import com.mobelite.locationvoiture.model.Notification;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NotificationDto {
    private Long id;
    private String message;
    private Long clientid;
    private Long adminid;

    //methode pour retourner une dto en donnant une entitee
    public static NotificationDto fromEntity(Notification notification) {
        if (notification == null){
            //to do exception
            return null;
        }
        return NotificationDto.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .clientid(notification.getClient() != null ? notification.getClient().getId() : null)
                .adminid(notification.getAdmin() != null ? notification.getAdmin().getId() : null)
                .build();
    }
    //dto---> entity
    public static Notification toEntity(NotificationDto notificationDto) {
        if (notificationDto == null){
            return null;
        }
        Notification notification = Notification.builder()
                .id(notificationDto.getId())
                .message(notificationDto.getMessage())
                .build();
        if (notificationDto.getClientid() != null) {
            Client client = new Client();
            client.setId(notificationDto.getClientid());
            notification.setClient(client);
        }
        if (notificationDto.getAdminid() != null) {
            Admin admin = new Admin();
            admin.setId(notificationDto.getAdminid());
            notification.setAdmin(admin);
        }
        return notification;
    }
}
