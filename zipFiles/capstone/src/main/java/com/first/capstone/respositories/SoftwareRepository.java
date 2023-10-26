package com.first.capstone.respositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.first.capstone.entity.Software;

public interface SoftwareRepository extends JpaRepository<Software, Long> {
    // You can define custom query methods if needed.


    @Query(value = "SELECT s FROM Software s WHERE s.manufacturer.fieldOfWork='Software'")
    List<Software> findAllByManufacturer();

    Optional <Software> findByIdAndSoftwareName(Long id, String softwareName);

    @Query("SELECT s FROM Software s WHERE s.expiryDate <= :today")
    List<Software> findExpiredSoftware(@Param("today") LocalDate today);
 
    @Query("SELECT s FROM Software s WHERE s.expiryDate > :today")
    List<Software> findNonExpiredSoftware(@Param("today") LocalDate today);




    
}
