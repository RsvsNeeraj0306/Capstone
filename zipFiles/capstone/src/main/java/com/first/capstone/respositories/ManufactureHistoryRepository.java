package com.first.capstone.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.first.capstone.entity.ManufactureHistory;

public interface ManufactureHistoryRepository  extends JpaRepository<ManufactureHistory, Long>{
    
}
