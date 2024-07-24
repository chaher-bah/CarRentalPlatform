package com.mobelite.locationvoiture.service.impl;

import com.mobelite.locationvoiture.model.reservationStatus;
import com.mobelite.locationvoiture.repository.carRepository;
import com.mobelite.locationvoiture.repository.clientRepository;
import com.mobelite.locationvoiture.repository.reservationRepository;
import com.mobelite.locationvoiture.service.DashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class DashServiceImpl implements DashService {
    private final clientRepository clientRepo;
    private final carRepository carRepo;
    private final reservationRepository reservationRepo;
    @Autowired
    public DashServiceImpl(clientRepository clientRepo, carRepository carRepo, reservationRepository reservationRepo) {
        this.clientRepo = clientRepo;
        this.carRepo = carRepo;
        this.reservationRepo = reservationRepo;
    }


    @Override
    public Map<String, Integer> getDashboardData() {
        Map<String, Integer> data = new HashMap<>();
        int totalClients = (int) clientRepo.count();
        int reservationsEnCour = reservationRepo.countByStatus(reservationStatus.EN_COUR);
        int reservationsAcc = reservationRepo.countByStatus(reservationStatus.ACCEPTEE);
        int reservationsRef = reservationRepo.countByStatus(reservationStatus.REFUSEE);
        data.put("nbrClients",totalClients);
        data.put("nbrReservations",reservationsEnCour);
        data.put("nbrReservationsAcc",reservationsAcc);
        data.put("nbrReservationsRef",reservationsRef);
        return data;
    }
}
