package com.mobelite.locationvoiture.repository;

import com.mobelite.locationvoiture.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface adminRepository extends JpaRepository<Admin, Integer> {
}
