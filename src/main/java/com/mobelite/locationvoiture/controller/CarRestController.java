package com.mobelite.locationvoiture.controller;

import com.mobelite.locationvoiture.dto.CarDto;
import com.mobelite.locationvoiture.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value = APP_ROUTE + "/cars/ajouter", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CarDto> addCar(
            @RequestPart("car") CarDto carDto,
            @RequestPart("images") List<MultipartFile> images) {
        CarDto savedCar = carservice.save(carDto);
        carservice.saveCarImages(savedCar.getId(), images);
        return ResponseEntity.ok(savedCar);
    }


    //    @PostMapping(APP_ROUTE+"/cars/ajouter")
//    public ResponseEntity<CarDto> save(@RequestBody CarDto car){
//        return new ResponseEntity<>(carservice.save(car), HttpStatus.CREATED);
//    }
    @GetMapping(value = APP_ROUTE+ "/cars/{carid}")
    public ResponseEntity<CarDto> getCar(@PathVariable("carid") Long carId){
        return new ResponseEntity<>(carservice.getCar(carId),HttpStatus.OK);
    }
    @GetMapping(value = APP_ROUTE+ "/cars")
    public ResponseEntity<List<CarDto>> getAllCars(){
        return new ResponseEntity<>(carservice.getAllCars(),HttpStatus.OK);
    }
    /*
    @Get :the car or cars by their marque
    @parma les marques
     */
    @GetMapping(value = APP_ROUTE+"/cars/marque/{marque}")
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
    @PatchMapping(value = APP_ROUTE+"/cars/{carid}/notdispo")
    public void updateDisponibiliteToFalse(@PathVariable("carid") Long carId){
        carservice.updateDisponibiliteToFalse(carId);
    }
    @PatchMapping(value = APP_ROUTE+"/cars/{carid}/dispo")
    public void updateDisponibiliteToTrue(@PathVariable("carid") Long carId){
        carservice.updateDisponibiliteToTrue(carId);
    }
    /*
    *@change the price of a car
     */
    @PatchMapping(value = APP_ROUTE+"/cars/{carid}/price/{price}")
    public ResponseEntity<CarDto> updatePrice(@PathVariable("carid") Long carId,@PathVariable("price") BigDecimal price){
        return new ResponseEntity<>(carservice.updateCarPrice(carId,price),HttpStatus.OK);
    }
    @PostMapping(value = APP_ROUTE+"/cars/{carId}/uploadImages")
    public ResponseEntity<String> uploadImages(@PathVariable("carId") Long carId,@RequestParam("files") List<MultipartFile> files){
        try {
            carservice.saveCarImages(carId, files);
            return new ResponseEntity<>("Files uploaded successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = APP_ROUTE+"/cars/{carId}/images")
    public ResponseEntity<List<byte[]>> getCarImages(@PathVariable("carId") Long carId){
        List<byte[]> images = carservice.getCarImages(carId);
        if (images != null ) {
            return new ResponseEntity<>(images, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = APP_ROUTE+"/cars/{id}/images/{imageIndex}")
    public ResponseEntity<byte[]> getCarImage(@PathVariable("id") Long id, @PathVariable("imageIndex") int imageIndex) {
        byte[] imageBytes = carservice.getCarImage(id, imageIndex);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageBytes);
    }
}
