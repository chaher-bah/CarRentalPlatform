package com.mobelite.locationvoiture.repository;

import com.mobelite.locationvoiture.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface clientRepository extends JpaRepository<Client, Integer> {
}
