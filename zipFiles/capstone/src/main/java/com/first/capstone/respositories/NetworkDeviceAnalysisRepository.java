package com.first.capstone.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.first.capstone.entity.NetworkDeviceAnalysis;


public interface NetworkDeviceAnalysisRepository extends JpaRepository<NetworkDeviceAnalysis, Long> {
    
}
