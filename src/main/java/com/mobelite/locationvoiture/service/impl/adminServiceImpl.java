package com.mobelite.locationvoiture.service.impl;
import com.mobelite.locationvoiture.repository.adminRepository;

import com.mobelite.locationvoiture.model.Admin;
import com.mobelite.locationvoiture.model.Car;
import com.mobelite.locationvoiture.service.adminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class adminServiceImpl implements adminService {
    private final adminRepository adminRepository;

    @Autowired
    public adminServiceImpl(adminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    @Override
    public void saveAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    @Override
    public void deleteAdmin(Admin admin) {
        adminRepository.delete(admin);
    }

    @Override
    public Optional<Admin> getAdmin(Long id) {
        return adminRepository.findById(id);
    }

    @Override
    public void addCars(Long adminId,List<Car> cars ) {
        adminRepository.updateCars(adminId,cars);
    }

    @Override
    public List<Car> getCars(Long adminId) {
        return adminRepository.findById(adminId).get().getCars();
    }
}
