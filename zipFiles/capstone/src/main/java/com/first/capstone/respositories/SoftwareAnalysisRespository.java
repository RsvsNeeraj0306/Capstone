package com.first.capstone.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.first.capstone.entity.SoftwareAnalysis;

public interface SoftwareAnalysisRespository extends JpaRepository<SoftwareAnalysis, Long>{

    void deleteBySoftwareId(Long softwareId);
    
}
