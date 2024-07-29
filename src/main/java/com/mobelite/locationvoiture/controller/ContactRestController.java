package com.mobelite.locationvoiture.controller;

import com.mobelite.locationvoiture.model.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.mobelite.locationvoiture.utils.constants.APP_ROUTE;

@RestController
@CrossOrigin(origins = "*")
public class ContactRestController {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String adminEmail;
    @PostMapping(value = APP_ROUTE+"/contact")
    public void sendContactEmail(@RequestBody ContactForm contactForm) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(contactForm.getEmail());
        message.setTo(adminEmail);
        message.setSubject("Client contact");
        message.setText(contactForm.getMessage());
        message.setText("Name: " + contactForm.getName() + "\n" +
                "Email: " + contactForm.getEmail() + "\n" +
                "Message: " + contactForm.getMessage());
        mailSender.send(message);
    }
}
