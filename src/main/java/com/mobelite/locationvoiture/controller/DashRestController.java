package com.mobelite.locationvoiture.controller;


import com.mobelite.locationvoiture.service.DashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.mobelite.locationvoiture.utils.constants.APP_ROUTE;

@CrossOrigin(origins = "*")
@RestController
public class DashRestController {
    private DashService dashService;
    @Autowired
    public DashRestController(DashService dashService) {
        this.dashService = dashService;
    }

    @GetMapping(APP_ROUTE+"/dash")
    private Map<String,Integer> getDashNbr() {
        return dashService.getDashboardData();
    }
}
