package com.first.capstone.Respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.first.capstone.Entity.Location;

import java.util.Optional;
import java.util.List;



public interface LocationRepository extends JpaRepository<Location, Long> {
    // You can define custom query methods if needed.

    Optional<Location> findByLocationName(String locationName);
}
