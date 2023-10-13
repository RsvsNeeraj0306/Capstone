package com.first.capstone.Respositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.first.capstone.Entity.Software;

public interface SoftwareRepository extends JpaRepository<Software, Long> {
    // You can define custom query methods if needed.


    @Query(value = "SELECT s FROM Software s WHERE s.manufacturer.fieldOfWork='Software'")
    List<Software> findAllByManufacturer();
}
