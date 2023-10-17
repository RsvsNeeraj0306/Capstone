package com.first.capstone.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.first.capstone.entity.Location;

import java.util.Optional;




public interface LocationRepository extends JpaRepository<Location, Long> {
    // You can define custom query methods if needed.

    Optional<Location> findByLocationName(String locationName);
}
