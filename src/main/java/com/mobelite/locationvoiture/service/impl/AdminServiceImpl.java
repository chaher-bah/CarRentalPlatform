package com.mobelite.locationvoiture.service.impl;
import com.mobelite.locationvoiture.dto.AdminDto;
import com.mobelite.locationvoiture.dto.CarDto;
import com.mobelite.locationvoiture.exception.EntityNotFoundException;
import com.mobelite.locationvoiture.exception.EntityNotValidException;
import com.mobelite.locationvoiture.exception.ErrorCodes;
import com.mobelite.locationvoiture.repository.adminRepository;
import com.mobelite.locationvoiture.repository.carRepository;

import com.mobelite.locationvoiture.model.Admin;
import com.mobelite.locationvoiture.model.Car;
import com.mobelite.locationvoiture.service.AdminService;
import com.mobelite.locationvoiture.validators.AdminValidator;
import com.mobelite.locationvoiture.validators.CarValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
    private final adminRepository adminRepository;
    private final carRepository carRepository;

    @Autowired
    public AdminServiceImpl(adminRepository adminRepository, com.mobelite.locationvoiture.repository.carRepository carRepository) {
        this.adminRepository = adminRepository;
        this.carRepository = carRepository;
    }
    @Override
    public AdminDto saveAdmin(AdminDto admin) {
        List<String> errors = AdminValidator.validation(admin);
        if (!errors.isEmpty()) {
            log.error("Car Validation errors: {}", errors);
            throw new EntityNotValidException("L'entite admin n'est pas valide", ErrorCodes.USER_NOT_VALID,errors);
        }
        return AdminDto.fromEntity(adminRepository.save(AdminDto.toEntity(admin)));
    }

    @Override
    public void deleteAdmin(Long adminId) {
        if (adminId == null){log.error("admin id is null");return;}
        if (!adminRepository.existsById(adminId)) {
            throw new EntityNotFoundException("l'admin avec ID "+adminId+" n'existe pas", ErrorCodes.USER_NOT_FOUND);
        }
        adminRepository.deleteById(adminId);
    }

    @Override
    public AdminDto getAdmin(Long id) {
        if (id == null){log.error("admin id is null");return null;}

        Optional<Admin> admin= adminRepository.findById(id);
        return Optional.of(AdminDto.fromEntity(admin.get())).orElseThrow(()->
                new EntityNotFoundException("l'admin avec ID "+id+" n'existe pas",ErrorCodes.USER_NOT_FOUND));
    }

    @Override
    public void addCars(Long adminId, List<Car> cars) {
        List<String> errors = CarValidator.validation(cars);
        if (!errors.isEmpty()) {
            log.error("Car Validation errors: {}", errors);
            throw new EntityNotValidException("L'entite voiture n'est pas valide", ErrorCodes.CAR_NOT_VALID, errors);
        }
        if (adminId == null || !adminRepository.existsById(adminId)) {
            log.error("admin id wrong");
            throw new EntityNotFoundException("L'admin avec ID " + adminId + " n'existe pas", ErrorCodes.USER_NOT_FOUND);
        }

        Admin admin = adminRepository.findById(adminId).orElseThrow(() ->
                new EntityNotFoundException("L'admin avec ID " + adminId + " n'existe pas", ErrorCodes.USER_NOT_FOUND));

        List<Car> savedCars = carRepository.saveAll(cars);
        admin.getCars().addAll(savedCars);
        adminRepository.save(admin);
    }

    @Override
    public List<CarDto> getCars(Long adminId) {
        List<Car> carsfound = adminRepository.findById(adminId).get().getCars();

        return carsfound
                .stream()
                .map(CarDto::fromEntity)
                .collect(Collectors.toList());
    }
    @Override
    public void removeCar(Long adminId, Long carId) {
        if (adminId == null || carId == null) {
            log.error("Admin ID or Car ID is null");
            throw new IllegalArgumentException("Admin ID or Car ID cannot be null");
        }

        Admin admin = adminRepository.findById(adminId).orElseThrow(() ->
                new EntityNotFoundException("L'admin avec ID " + adminId + " n'existe pas", ErrorCodes.USER_NOT_FOUND));

        Car car = carRepository.findById(carId).orElseThrow(() ->
                new EntityNotFoundException("La voiture avec ID " + carId + " n'existe pas", ErrorCodes.CAR_NOT_FOUND));

        admin.getCars().remove(car);
        adminRepository.save(admin);
        carRepository.deleteById(carId);
    }
}
