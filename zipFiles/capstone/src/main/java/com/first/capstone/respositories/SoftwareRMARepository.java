package com.first.capstone.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.first.capstone.entity.SoftwareRMA;

public interface SoftwareRMARepository extends JpaRepository<SoftwareRMA, Long>{

    void deleteBySoftwareId(Long softwareId);
    
}
