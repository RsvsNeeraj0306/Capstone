package com.first.capstone.respositories;

import java.util.List;
import java.util.Optional;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.first.capstone.entity.Manufacturer;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    Optional<Manufacturer> findByname(String name);
    boolean existsByNameAndFieldOfWork(String name, String fieldOfWork);
    Optional<Manufacturer>  findByNameAndFieldOfWork(String name, String fieldOfWork);

    @Query("SELECT m FROM Manufacturer m WHERE m.fieldOfWork = 'Software'")
    List<Manufacturer> findByFieldOfWorkSoftware();

    @Query("SELECT m FROM Manufacturer m WHERE m.fieldOfWork = 'Hardware'")
    List<Manufacturer> findByFieldOfWorkHardware();

}
