package com.mobelite.locationvoiture.service;

import com.mobelite.locationvoiture.dto.AdminDto;
import com.mobelite.locationvoiture.dto.CarDto;
import com.mobelite.locationvoiture.model.Admin;
import com.mobelite.locationvoiture.model.Car;

import java.util.List;
import java.util.Optional;

public interface adminService {
    public AdminDto saveAdmin(AdminDto admin);
    public void deleteAdmin(Long adminid);
    public AdminDto getAdmin(Long id);
    public void addCars(Long adminId,List<Car> cars);
    public List<CarDto> getCars(Long adminId);

}
