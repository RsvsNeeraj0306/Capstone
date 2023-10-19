package com.first.capstone.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.first.capstone.entity.NetworkDevicesHistory;

public interface NetworkDevicesHistoryRepository extends JpaRepository<NetworkDevicesHistory, Long>{
    
}
