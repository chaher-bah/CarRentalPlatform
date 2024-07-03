package com.mobelite.locationvoiture.service;

import com.mobelite.locationvoiture.dto.AdminDto;
import com.mobelite.locationvoiture.dto.CarDto;
import com.mobelite.locationvoiture.model.Car;

import java.util.List;

public interface AdminService {
    AdminDto saveAdmin(AdminDto admin);
    void deleteAdmin(Long adminid);
    AdminDto getAdmin(Long id);
    void addCars(Long adminId,List<Car> cars);
    List<CarDto> getCars(Long adminId);
    void removeCar(Long adminId,Long carid);

}
