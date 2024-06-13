package com.mobelite.locationvoiture.controller;

import com.mobelite.locationvoiture.dto.CarDto;
import com.mobelite.locationvoiture.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

import static com.mobelite.locationvoiture.utils.constants.APP_ROUTE;

@CrossOrigin(origins = "*")
@RestController
public class CarRestController {
    private final CarService carservice;
    @Autowired
    public CarRestController(CarService carservice) {
        this.carservice = carservice;
    }

    @PostMapping(APP_ROUTE+"/cars/ajouter")
    public CarDto save(@RequestBody CarDto car){
        return carservice.save(car);
    }
    @GetMapping(value = APP_ROUTE+ "/cars/{carid}")
    public CarDto getCar(@PathVariable("carid") Long carId){
        return carservice.getCar(carId);
    }
    @GetMapping(value = APP_ROUTE+ "/cars/all")
    public List<CarDto> getAllCars(){
        return carservice.getAllCars();
    }
    @DeleteMapping(value = APP_ROUTE+"/cars/delete/{carid}")
    public void deleteCar(@PathVariable("carid")Long carId){
        carservice.deleteCar(carId);
    }
    @GetMapping(value = APP_ROUTE+"/cars/{marque}")
    public List<CarDto> getCarsbymarque(@PathVariable("marque") String marque){
        return carservice.getCarsbymarque(marque);
    }
    @GetMapping(value = APP_ROUTE+"/cars/disponibilite?=true")
    public List<CarDto> getCarsByDisponibilite(){
        return carservice.getCarsByDisponibilite();
    }
    @GetMapping(value = APP_ROUTE+"/cars/price/{price}")
    public List<CarDto> getCarsByPrice(@PathVariable("price") BigDecimal price){
        return carservice.getCarsByPrice(price);
    }
    @PutMapping(value = APP_ROUTE+"/cars/{carid}/updatedipo?=false")
    public void updateDisponibiliteToFalse(@PathVariable("carid") Long carId){
        carservice.updateDisponibiliteToFalse(carId);
    }
    @PutMapping(value = APP_ROUTE+"/cars/{carid}/updatedispo?=true")
    public void updateDisponibiliteToTrue(@PathVariable("carid") Long carId){
        carservice.updateDisponibiliteToTrue(carId);
    }
}
