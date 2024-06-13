package com.mobelite.locationvoiture.controller;

import com.mobelite.locationvoiture.dto.AdminDto;
import com.mobelite.locationvoiture.dto.CarDto;
import com.mobelite.locationvoiture.model.Car;
import com.mobelite.locationvoiture.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('Admin_Role')")
    @PostMapping(APP_ROUTE+"/admin/ajouter")
    public ResponseEntity<AdminDto> saveAdmin(@RequestBody AdminDto admin){
        return new ResponseEntity<>(adminService.saveAdmin(admin), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('Admin_Role')")
    @GetMapping(APP_ROUTE+"/admin/{adminid}")
    public ResponseEntity<AdminDto> getAdmin(@PathVariable("adminid") Long id){
        return new ResponseEntity<>(adminService.getAdmin(id),HttpStatus.OK);
    }
    @PreAuthorize("hasRole('Admin_Role')")
    @GetMapping(APP_ROUTE+"/admin/{adminid}/voitures")
    public ResponseEntity<List<CarDto>> getCars(@PathVariable("adminid") Long adminId){
        return new ResponseEntity<>(adminService.getCars(adminId),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Admin_Role')")
    @PutMapping(APP_ROUTE+"/admin/{adminid}/ajoutervoitures")
    public void addCars(@PathVariable("adminid") Long adminId, List<Car> cars){
        adminService.addCars(adminId, cars);
    }

    @PreAuthorize("hasRole('Admin_Role')")
    @DeleteMapping(APP_ROUTE+"/admin/delete/{adminid}")
    public void deleteAdmin(@PathVariable("adminid") Long adminid){
        adminService.deleteAdmin(adminid);
    }
}
