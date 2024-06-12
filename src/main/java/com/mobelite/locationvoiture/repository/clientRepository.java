package com.mobelite.locationvoiture.repository;

import com.mobelite.locationvoiture.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface clientRepository extends JpaRepository<Client, Long> {
    @Query("select c from Client c where c.email = ?1")
    Client findByEmail(String email);

    @Query("select c from Client c where c.cin = ?1")
    Client findByCin(String cin);
}
