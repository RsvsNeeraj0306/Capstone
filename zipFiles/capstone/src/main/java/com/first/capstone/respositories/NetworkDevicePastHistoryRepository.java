package com.first.capstone.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.first.capstone.entity.NetworkDevicePastHistory;

public interface NetworkDevicePastHistoryRepository extends JpaRepository<NetworkDevicePastHistory, Long> {
    
}
