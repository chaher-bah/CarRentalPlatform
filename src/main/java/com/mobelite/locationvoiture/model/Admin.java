package com.mobelite.locationvoiture.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "admins")
public class Admin extends User {
    private String localisation;
    @OneToMany(targetEntity = Car.class)
    private List<Car> Cars;
    @OneToMany(targetEntity = Notification.class)
    private List<Notification> notifications;
}
