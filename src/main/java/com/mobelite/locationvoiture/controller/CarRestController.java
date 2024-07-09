package com.mobelite.locationvoiture.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobelite.locationvoiture.dto.CarDto;
import com.mobelite.locationvoiture.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.mobelite.locationvoiture.model.Car;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public ResponseEntity<Car> addCar(
            @RequestPart("car") String carJson,
            @RequestPart("images") List<MultipartFile> images) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Car car = objectMapper.readValue(carJson, Car.class);
        Car savedCar = carservice.save(car);
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
    @GetMapping(value = APP_ROUTE+ "/admin/cars")
    public ResponseEntity<List<Car>> getAllCarsAdmin(){
        return new ResponseEntity<>(carservice.getAllCarsAdmin(),HttpStatus.OK);
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

    @PostMapping(value = APP_ROUTE + "/cars/{carId}/uploadImages")
    public ResponseEntity<String> uploadImages(@PathVariable("carId") Long carId, @RequestParam("files") List<MultipartFile> files) {
        try {
            carservice.saveCarImages(carId, files);
            return new ResponseEntity<>("Files uploaded successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = APP_ROUTE + "/cars/{carId}/images")
    public ResponseEntity<List<byte[]>> getCarImages(@PathVariable("carId") Long carId) {
        List<byte[]> imageBytes = carservice.getCarImages(carId);
        if (imageBytes != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(imageBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping(value = APP_ROUTE + "/cars/{id}/images/{imageIndex}")
    public ResponseEntity<byte[]> getCarImage(@PathVariable("id") Long id, @PathVariable("imageIndex") int imageIndex) {
        byte[] imageBytes = carservice.getCarImage(id, imageIndex);
        if (imageBytes != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"image_" + id + "_" + imageIndex + ".jpg\"")
                    .body(imageBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
