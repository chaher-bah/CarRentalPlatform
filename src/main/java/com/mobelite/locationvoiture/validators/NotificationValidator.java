package com.mobelite.locationvoiture.validators;

import com.mobelite.locationvoiture.dto.NotificationDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class NotificationValidator {
    public static List<String> validation(NotificationDto notificationDto) {
        List<String> errors = new ArrayList<String>();
        if (notificationDto == null) {
            errors.add("Notification n'est pas valide");
            errors.add("Message n'est pas valide");
            errors.add("Destinataire n'est pas valide");
        }
        assert notificationDto != null;
        if (!StringUtils.hasText(notificationDto.getMessage())) {
            errors.add("Message n'est pas valide [Il faut avoir au moin un mot ]");
        }
        if (notificationDto.getClientid() == null && notificationDto.getAdminid() == null) {
            errors.add("Il faut fournir au moins un destinataire (clientid ou adminid)");
        }

        return errors;
    }
}
