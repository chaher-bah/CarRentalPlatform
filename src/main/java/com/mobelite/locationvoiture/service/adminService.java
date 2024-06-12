package com.mobelite.locationvoiture.service;

import com.mobelite.locationvoiture.model.Admin;
import com.mobelite.locationvoiture.model.Car;

import java.util.List;
import java.util.Optional;

public interface adminService {
    public void saveAdmin(Admin admin);
    public void deleteAdmin(Admin admin);
    public Optional<Admin> getAdmin(Long id);
    public void addCars(Long adminId,List<Car> cars);
    public List<Car> getCars(Long adminId);

}
