package com.mobelite.locationvoiture.controller;

import com.mobelite.locationvoiture.dto.CarDto;
import com.mobelite.locationvoiture.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

import static com.mobelite.locationvoiture.utils.constants.APP_ROUTE;

@RestController
public class CarRestController {
    private final CarService carservice;
    @Autowired
    public CarRestController(CarService carservice) {
        this.carservice = carservice;
    }

    @PostMapping(APP_ROUTE+"/cars/ajouter")
    public ResponseEntity<CarDto> save(@RequestBody CarDto car){
        return new ResponseEntity<>(carservice.save(car), HttpStatus.CREATED);
    }
    @GetMapping(value = APP_ROUTE+ "/cars/{carid}")
    public ResponseEntity<CarDto> getCar(@PathVariable("carid") Long carId){
        return new ResponseEntity<>(carservice.getCar(carId),HttpStatus.OK);
    }
    @GetMapping(value = APP_ROUTE+ "/cars/all")
    public ResponseEntity<List<CarDto>> getAllCars(){
        return new ResponseEntity<>(carservice.getAllCars(),HttpStatus.OK);
    }
    /*
    @Get :the car or cars by their marque
    @parma les marques
     */
    @GetMapping(value = APP_ROUTE+"/cars/{marque}")
    public ResponseEntity<List<CarDto>> getCarsbymarque(@PathVariable("marque") String marque){
        return new ResponseEntity<>(carservice.getCarsbymarque(marque),HttpStatus.OK);
    }
    /*
    @get : the cars with the disponibilite set to true
     */
    @GetMapping(value = APP_ROUTE+"/cars/disponibilite=true")
    public ResponseEntity<List<CarDto>> getCarsByDisponibilite(){
        return new ResponseEntity<>(carservice.getCarsByDisponibilite(),HttpStatus.OK);
    }
    /*
    @Get all the cars that exicts within the range of the price given
     */
    @GetMapping(value = APP_ROUTE+"/cars/price/{price}")
    public ResponseEntity<List<CarDto>> getCarsByPrice(@PathVariable("price") BigDecimal price){
        return new ResponseEntity<>(carservice.getCarsByPrice(price),HttpStatus.OK);
    }
    /*
    @changer la disponibilite
     */
    @PatchMapping(value = APP_ROUTE+"/cars/{carid}/updatedipo?=false")
    public void updateDisponibiliteToFalse(@PathVariable("carid") Long carId){
        carservice.updateDisponibiliteToFalse(carId);
    }
    @PatchMapping(value = APP_ROUTE+"/cars/{carid}/updatedispo=true")
    public void updateDisponibiliteToTrue(@PathVariable("carid") Long carId){
        carservice.updateDisponibiliteToTrue(carId);
    }
    /*
    delete a car by its id
     */
    @DeleteMapping(value = APP_ROUTE+"/cars/delete/{carid}")
    public void deleteCar(@PathVariable("carid")Long carId){
        carservice.deleteCar(carId);
    }
    /*
    *@change the price of a car
     */
    @PatchMapping(value = APP_ROUTE+"/cars/{carid}/update/{price}")
    public ResponseEntity<CarDto> updatePrice(@PathVariable("carid") Long carId,@PathVariable("price") BigDecimal price){
        return new ResponseEntity<>(carservice.updateCarPrice(carId,price),HttpStatus.OK);
    }
}
