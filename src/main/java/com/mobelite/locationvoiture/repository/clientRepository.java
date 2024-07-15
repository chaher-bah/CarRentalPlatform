package com.mobelite.locationvoiture.repository;

import com.mobelite.locationvoiture.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface clientRepository extends JpaRepository<Client, Long> {
    @Query("select c from Client c where c.email = ?1")
    List<Client> findByEmail(String email);

    @Query("select c from Client c where c.cin = ?1")
    List<Client> findByCin(String cin);
    List<Client> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom);

}
