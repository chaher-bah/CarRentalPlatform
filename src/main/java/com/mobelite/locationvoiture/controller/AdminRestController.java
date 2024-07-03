package com.mobelite.locationvoiture.controller;

import com.mobelite.locationvoiture.dto.AdminDto;
import com.mobelite.locationvoiture.dto.CarDto;
import com.mobelite.locationvoiture.model.Car;
import com.mobelite.locationvoiture.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mobelite.locationvoiture.utils.constants.APP_ROUTE;

@CrossOrigin(origins = "*")
@RestController
public class AdminRestController {

    private final AdminService adminService;
    @Autowired
    public AdminRestController(AdminService adminService) {
        this.adminService = adminService;
    }
//    @PreAuthorize("hasRole('Admin_Role')")
    @PostMapping(APP_ROUTE+"/admin/ajouter")
    public ResponseEntity<AdminDto> saveAdmin(@RequestBody AdminDto admin){
        return new ResponseEntity<>(adminService.saveAdmin(admin), HttpStatus.CREATED);
    }
    /*
     *@get Admin:getting the admin by id
     * @parm:the id of the admin
     * @return :the admin exicts in data base
     */
//    @PreAuthorize("hasRole('Admin_Role')")
    @GetMapping(APP_ROUTE+"/admin/{adminid}")
    public ResponseEntity<AdminDto> getAdmin(@PathVariable("adminid") Long id){
        return new ResponseEntity<>(adminService.getAdmin(id),HttpStatus.OK);
    }
    /*
    *@get cars: getting the list of cars of an admin
    * @parm admin id: the id of an admin
    * @return: a list of car object
     */
//    @PreAuthorize("hasRole('Admin_Role')")
    @GetMapping(APP_ROUTE+"/admin/{adminid}/voitures")
    public ResponseEntity<List<CarDto>> getCars(@PathVariable("adminid") Long adminId){
        return new ResponseEntity<>(adminService.getCars(adminId),HttpStatus.OK);
    }
    /*
    *@add cars :adding new cars to the list of exicting cars
    * @parm admin id : the id of an admin
    * cars: list of the object car
    */
//    @PreAuthorize("hasRole('Admin_Role')")
    @PatchMapping(APP_ROUTE+"/admin/{adminid}/ajoutervoitures")
    public void addCars(@PathVariable("adminid") Long adminId,@RequestBody List<Car> cars){
        adminService.addCars(adminId, cars);
    }
    /*
    *@Deleteadmin :deleting an admin
    * @parm: the id of an admin
     */
//    @PreAuthorize("hasRole('Admin_Role')")
    @DeleteMapping(APP_ROUTE+"/admin/{adminid}")
    public void deleteAdmin(@PathVariable("adminid") Long adminid){
        adminService.deleteAdmin(adminid);
    }

    @DeleteMapping(APP_ROUTE+"/admin/{adminid}/voiture/{carid}")
    public ResponseEntity<Void> removeCar(@PathVariable("adminid") Long adminId, @PathVariable("carid") Long carId) {
        adminService.removeCar(adminId, carId);
        return ResponseEntity.noContent().build();
    }
}
