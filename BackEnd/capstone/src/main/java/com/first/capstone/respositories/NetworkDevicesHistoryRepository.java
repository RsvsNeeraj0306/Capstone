package com.first.capstone.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.first.capstone.entity.NetworkDevicesHistory;

public interface NetworkDevicesHistoryRepository extends JpaRepository<NetworkDevicesHistory, Long>{

    @Query("select n from NetworkDevicesHistory n where n.action=?1")
    NetworkDevicesHistory findByAction(String action);
    
}
