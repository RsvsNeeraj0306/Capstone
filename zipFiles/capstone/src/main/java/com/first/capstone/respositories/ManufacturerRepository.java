package com.first.capstone.respositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.first.capstone.entity.Manufacturer;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    Optional<Manufacturer> findByname(String name);
    boolean existsByNameAndFieldOfWork(String name, String fieldOfWork);
    Optional<Manufacturer>  findByNameAndFieldOfWork(String name, String fieldOfWork);

}